<?php

require_once('database/Database.php');
$db = new Database();
$requestData = json_decode(file_get_contents('php://input'), true);
$date = $requestData['date'];

$sql = ("SELECT c.opportunity_id as id,o.name as oppname,q.name as cusn, q.mobileno as mno,
x.contact, s.stage,u.username,date(c.created_at),
 date_format(date(c.created_at),'%d-%m-%Y') as visitdate
FROM calls c
inner join opportunities o on o.id = c.opportunity_id
left join customers q on q.id = o.customer_id
left join contacts x on x.id = c.contact_id
left join stages s on s.id = c.stage_id
left join users u on u.id = c.user_id
where c.reminddate = '$date' ");

$list = $db->getRows($sql);

$response['response'] = $list;
echo json_encode($response);

?>

