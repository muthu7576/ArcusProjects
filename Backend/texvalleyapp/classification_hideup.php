
<?php

require_once('database/Database.php');
$db = new Database();


$requestData = json_decode(file_get_contents('php://input'), true);
$id = $requestData['id'];

$sql =  "SELECT name
        FROM classifications_hideup s
        where name
        in (select o.classification from opportunities o where o.id = '$id' )
        Union
        SELECT name
        FROM classifications_hideup s
        where name
        not in (select o.classification from opportunities o where o.id = '$id' ) ";
$shu1 = $db->getRows($sql);

$response['response'] = $shu1;


echo json_encode($response);
$db->Disconnect();
?>


