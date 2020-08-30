<?php
    function sql_connect(){
        $sql = mysqli_connect("", "", "", ""); // адрес сервера бд, логин, пароль, название базы данных
        if($sql == false){
            print("Ошибка: Невозможно подключиться к MySQL " . mysqli_connect_error());
            return false;
        }
        return true;
    }