
<?php

require_once('database/Database.php');
$db = new Database();


$requestData = json_decode(file_get_contents('php://input'), true);
$id = $requestData['id'];

$sql = "SELECT id,stage
        FROM stages s
        where id
        in (select o.stage_id from opportunities o where o.id = '$id' )
        Union
        SELECT id,stage
        FROM stages s
        where id
        not in (select o.stage_id from opportunities o where o.id = '$id' ) AND (s.deleted = 0)";

$shu = $db->getRows($sql);

$response['response'] = $shu;

echo json_encode($response);
$db->Disconnect();

?>


