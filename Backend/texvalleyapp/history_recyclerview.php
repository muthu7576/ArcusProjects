<?php

require_once('database/Database.php');
$db = new Database();

$requestData = json_decode(file_get_contents('php://input'), true);
$id = $requestData['id'];

$sql = ("SELECT c.id,c.opportunity_id,x.contact, s.stage,u.username,date(c.created_at),date_format(date(c.created_at),'%d-%m-%Y') as visitdate
FROM calls c
left join contacts x on x.id = c.contact_id
left join stages s on s.id = c.stage_id
left join users u on u.id = c.user_id
where c.opportunity_id = '$id'");

$list = $db->getRows($sql);

$response['response'] = $list;
echo json_encode($response);

?>

