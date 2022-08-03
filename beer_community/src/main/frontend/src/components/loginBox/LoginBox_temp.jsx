import React, { useState } from "react";
import styles from "./loginBox.module.css";

import { Link } from "react-router-dom";
import axios from 'axios';

const LoginBox = () => {
  const [loginState, setLoginState] = useState(true);
  const [emailList, setEmailList] = useState([]);
  const onClickLoginButton = (e) => {
    e.preventDefault();
    setLoginState(true);
  };
  const onClickSignUpButton = async (e) => {
    e.preventDefault();
    await axios.get('/api/join')
    .then((response) => {
                    console.log('response: ', response);
                    console.log('type of response: ', typeof(response)); // Map형식으로 return하면 object형식임
                    console.log('status: ', response.status); // 200
                    console.log('response.data: ', response.data); //
                    console.log('response.json: ', response.json); // 200

//                     return response.json();
                    return response.data
                })
                .then(function (data) {
                    console.log('data: ', data)
                    console.log('type of data: ', typeof(data));
                    // ['ko1@naver.com', 'ko1@naver.com', 'ko2@naver.com', 'kyunghwanko1207@gmail.com', 'ko3@naver.com', 'gkw1207@gmail.com', 'gkw1207@naver.com', 'gkw1207@likelion.org']
                    console.log('len of data: ', data.length)
                    console.log('0 of data: ', data[0]) // ko1@naver.com

//                     for (var i = 0; i < data.length; i++){
//                         setEmailList([ ...emailList, data[i] ]);
// //                         console.log(i, '. ', emailList);
//                     }
                    setEmailList(data);
                    console.log('data . id', data.id);
                    console.log('type of emailList: ', typeof(emailList));
                    console.log('emailList: ', emailList); // []
                    console.log('int logstatu: ', loginState);
                    // {userInfo.map((text, index) => <li key={`${index}-${text}`}>{text}</li>)}
                });
    setLoginState(false);
    console.log('out logstatu: ', loginState);
  };

  return (
    <div className={styles.main}>
      <div className={styles.loginBox}>
        <div className={styles.imgDiv}>
          <img src="/img/logo.png" alt="네캔만원" className={styles.loginImg} />
        </div>
        {loginState === true ? ( // 로그인 화면
          <>
            <form action="/login" method="POST">
              <button className={styles.Button} onClick={onClickSignUpButton}>
                회원가입
              </button>
              <button
                className={styles.onClickButton}
                onClick={onClickLoginButton}
              >
                로그인
              </button>
              <label className={styles.label}>이메일</label>
              <input type="text" className={styles.loginInput} name="email"/>
              <label className={styles.label}>비밀번호</label>
              <input type="password" className={styles.passwordInput} name="password"/>
              <button className={styles.submitButton} type="submit">
                로그인
              </button>
            </form>
            <div className={styles.loginAnother}>
              <div className={styles.loginAnotherLine}></div>
              <div className={styles.loginAnotherTitle}>
                다른 서비스로 로그인
              </div>
            </div>
            <div className={styles.loginAnotherButtonBox}>
              <a href="http://localhost:8080/oauth2/authorization/naver"
                className={[styles.loginAnotherButton, styles.naver].join(" ")}>
                {/* 라이브러리 classnames 설치해도 된다.*/}
              </a>

              <a href="http://localhost:8080/oauth2/authorization/google"

                className={[styles.loginAnotherButton, styles.google].join(" ")}>
              </a>
            </div>
          </>
        ) : (
          // 회원가입 화면
          <>
            <form action="/api/join" method="POST">
              <button
                className={styles.onClickButton}
                onClick={onClickSignUpButton}
              >
                회원가입
              </button>
              <button className={styles.Button} onClick={onClickLoginButton}>
                로그인
              </button>
              <label className={styles.label}>이메일</label>
              <input type="email" name="email" className={styles.loginInput} />
              <label className={styles.label}>닉네임</label>
              <input type="text" name="username" className={styles.loginInput} />
              <label className={styles.label}>비밀번호</label>
              <input type="password" name="password" className={styles.loginInput} />
              <label className={styles.label}>비밀번호 확인</label>
              <input type="password" className={styles.loginInput} />
              <button className={styles.submitButton} type="submit">
                회원가입
              </button>
            </form>
          </>
        )}
      </div>
    </div>
  );
};

export default LoginBox;
