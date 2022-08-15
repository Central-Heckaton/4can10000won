import React, { useEffect, useState } from "react";

import LoginBox from "../components/loginBox/LoginBox";
const LoginRetry = () => {
    useEffect(() => {
        alert("로그인에 실패하였습니다.");
    }, [])
  return (
    <>
     <LoginBox />
    </>
  );
};

export default LoginRetry;
