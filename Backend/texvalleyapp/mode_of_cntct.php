
<?php

require_once('database/Database.php');
$db = new Database();

$sql = "SELECT id,contact FROM contacts where deleted = 0";
$moc = $db->getRows($sql);

$response['response'] = $moc;

echo json_encode($response);
$db->Disconnect();

?>


