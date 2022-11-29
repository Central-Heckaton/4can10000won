import {useEffect, useState} from "react";
import axios from 'axios';

function UserInfo() {
    const [userInfo, setUserInfo] = useState([]);
    console.log('enter/userInfo');
    useEffect(() => {
        axios.get("/api/user")
            .then((response) => {
                return response.data
            })
            .then(function (data) {
                console.log('data: ', data) // {email: "ko2@naver.com", username: "ko2"}
                setUserInfo(data);
            });
    }, []);

    return (
        <div>
            <header>
                <ul>이메일: {userInfo.email}</ul>
                <ul>이름: {userInfo.username}</ul>
            </header>
        </div>
    );
}

export default UserInfo;