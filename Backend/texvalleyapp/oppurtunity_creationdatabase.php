<?php

require_once('database/Database.php');
$db = new Database();

$requestData = json_decode(file_get_contents('php://input'), true);
$contactnumber = $requestData['contactnumber'];
$name = $requestData['name'];
$email = $requestData['email'];
$website = $requestData['website'];
$companyname = $requestData['companyname'];
$brandname = $requestData['brandname'];
$address = $requestData['address'];
$area = $requestData['area'];
$customercategory = $requestData['customercategory'];
$customerstatus = $requestData['customerstatus'];
$products = $requestData['products'];
$sql1 = ("INSERT INTO customers (name,companyname,area,mobileno,email,website,brandname,address,customercategory,products,customerstatus)
         VALUES ('$name','$companyname','$area','$contactnumber','$email','$website','$brandname','$address','$customercategory','$products','$customerstatus')");

$z1= $db->insertRow($sql1);

//$check = ("SELECT id from customers where name = '$name' and mobileno = '$contactnumber'");
//$mysqli->query($check);
//while ($rowcheck = mysqli_fetch_assoc($check)){
//    $result[]= $rowcheck;
//}
if($z1){
$sql2 =("SELECT id from customers order by id desc limit 1");
$z2= $db->getRow($sql2);
    $r = $z2['id'];

if($z2){
$opportunityname = $requestData['oppurtunityname'];
$expecteddate = $requestData['expecteddate'];
$categorystatuslists = $requestData['categorystatuslists'];
$opptySalesstage = $requestData['opptySalesstage'];
$values = $requestData['values'];
$enquirylist = $requestData['enquirylist'];
$leadsrcestatus = $requestData['leadsrcestatus'];
$descriptionleadsrce = $requestData['descriptionleadsrce'];
$description = $requestData['description'];
$Assignto = $requestData['Assignto'];
$Remainderdate = $requestData['Remainderdate'];
$Time = $requestData['Time'];



$sql3 = ("INSERT INTO opportunities (name,expCloseDate,classification,customer_id,stage_id,amt,enquiry_id,description,lead_source1,l1_description,user_id,
        reminderDate,reminder_time) 
        VALUES ('$opportunityname','$expecteddate','$categorystatuslists','$r','$opptySalesstage','$values','$enquirylist',
        '$descriptionleadsrce','$leadsrcestatus','$description','$Assignto','$Remainderdate','$Time')");
$z3 = $db->insertRow($sql3);


}
}



if($z3 && $z2 && $z1){
    $response['status'] = 200;
    $response['cno'] = $contactnumber;
}
else{
    $response['status'] = 0;
    
}
echo json_encode($response);
mysqli_close($db);
?>

