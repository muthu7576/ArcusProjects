<?php

require_once('database/Database.php');
$db = new Database();
$requestData = json_decode(file_get_contents('php://input'), true);
$id = $requestData['id'];
$sql = ("SELECT o.id,o.name,o.expCloseDate,o.classification,o.amt,c.name as cus,s.stage,
i.categoryname as cuscat, u.username as u,z.customerStatus as cs,c.companyname as cn
FROM opportunities o
left join customers c on c.id = o.customer_id
left join stages s on s.id = o.stage_id
left join customer_categories i on i.id = c.customercategory
left join users u on u.id = o.user_id
left join customer_statuses z on z.id = c.customerstatus
where o.id = '$id'");

$list = $db->getRow($sql);

//        $expctdte= $list['o.expCloseDate'];
//        $clssfy = $list['o.classification'];
//        $amnt = $list['o.amt'];
//        $stge= $list['s.stage'];
//        $cuscatgry= $list['cuscat'];
//        $cussts = $list['cs'];
//        $cmpnyname= $list['cn'];
//        
//if($expctdte == null){
//    $expctdte1 = "-";
//}
//if($expctdte != null){
//    $expctdte1 = $expctdte;
//}
//if($clssfy == null){
//    $clssfy1 = "-";
//}
//if($clssfy != null){
//    $clssfy1 = $clssfy;
//}
//if($expctdte == null){
//    $expctdte1 = "-";
//}
//if($expctdte != null){
//    $expctdte1 = $expctdte;
//}
//if($expctdte == null){
//    $expctdte1 = "-";
//}
//if($expctdte != null){
//    $expctdte1 = $expctdte;
//}
//if($expctdte == null){
//    $expctdte1 = "-";
//}
//if($expctdte != null){
//    $expctdte1 = $expctdte;
//}
//if($expctdte == null){
//    $expctdte1 = "-";
//}
//if($expctdte != null){
//    $expctdte1 = $expctdte;
//}
//if($expctdte == null){
//    $expctdte1 = "-";
//}
//if($expctdte != null){
//    $expctdte1 = $expctdte;
//}
$response['response'] = $list;

echo json_encode($response);
$db->Disconnect();
?>

