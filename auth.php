<?php
    include 'config.php';

    $user = $_GET["user"];
    $pass = $_GET["pass"];

    if(!($sql = sql_connect)){
        $result = mysqli_query($sql, "SELECT * FROM groups WHERE user = '${user}'");
        if(mysqli_num_rows($result) == 0){
            print("Wrong username");
        } else{
            $row = mysqli_fetch_array($result);
            if($row['pass'] == $pass){
                print("Logged as student - ");
                $key = bin2hex(random_bytes(32));
                $err = mysqli_query($sql, "INSERT INTO `sessions` (`id`, `groupId`, `isRoot`) VALUES ('$key', '{$row['id']}', '0')");
                if($err == false){
                    print("Error, try later!");
                } else{
                    print($key);
                }
            } else if ($row['root'] == $pass){
                print("Logged as headman - ");
                $key = bin2hex(random_bytes(32));
                $err = mysqli_query($sql, "INSERT INTO `sessions` (`id`, `groupId`, `isRoot`) VALUES ('$key', '{$row['id']}', '1')");
                if($err == false){
                    print("Error, try later!");
                } else{
                    print($key);
                }
            } else {
                print("Wrong password");
            }
        }
    }