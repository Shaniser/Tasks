<?php
    include 'config.php';

    $sessionID = $_GET["sessionID"];
    $action = $_GET["action"];
    $json = $_GET["json"];

    function getGroupID($sql, $sessionID){
        if($result = $sql->query("SELECT * FROM sessions WHERE id = '${sessionID}'")){
            if($row = $result->fetch_array(MYSQLI_ASSOC)) {
                return $row['groupID'];
            }
        }
    }

    if(!($sql = sql_connect){
        $tablename = "group_" . getGroupID($sql, $sessionID);
        switch($action) {
            case 'loadEvents':
                $myArray = array();
                if ($result = $sql->query("SELECT * FROM ${tablename}")) {
                    while($row = $result->fetch_array(MYSQLI_ASSOC)) {
                        $myArray[] = $row;
                    }
                    print(json_encode($myArray));
                }
                break;
            case 'addEvent':
                if($json != ""){
                    $event = json_decode($json);
                    if(!mysqli_query($sql, "INSERT INTO ${tablename} (`id`, `header`, `content`, `date`, `timeBegin`, `timeEnd`, `type`, `classroom`, `teacher`, `isNotEveryWeek`) VALUES (NULL, '{$event->header}', '{$event->content}', '{$event->date}', '{$event->timeBegin}', '{$event->timeEnd}', '{$event->type}', '{$event->classroom}', '{$event->teacher}', '{$event->isNotEveryWeek}')")){
                        print("Error");
                    } else{
                        print($result = $sql->query("SELECT * FROM ${tablename} WHERE id = '${sessionID}'")
                    }
                }
                break;
                case '':

                
        }
    }
