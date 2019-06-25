<?php

require_once('database/Database.php');
$db = new Database();

$sql = ("SELECT o.id,o.name,c.name as cus,c.mobileno as mno
FROM `opportunities` o
inner join customers c on c.id=o.customer_id");

$list = $db->getRows($sql);

$response['response'] = $list;
echo json_encode($response);

?>

