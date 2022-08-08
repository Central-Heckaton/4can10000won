import React, { useState } from "react";
import styles from "./loginBox.module.css";
import axios from 'axios';

const LoginBox = () => {
  const [loginState, setLoginState] = useState(true);
  const [emailList, setEmailList] = useState([]);
  const [message, setMessage] = useState('');
  const [checkEmailDuplicate, setCheckEmailDuplicate] = useState(false);
  // -- 회원가입시 사용할 데이터 --
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmedPassword, setConfirmedPassword] = useState("");
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [day, setDay] = useState("");
  const [birthday, setBirthday] = useState("");


  // ---------
  const onClickLoginButton = (e) => {
    e.preventDefault();
    setLoginState(true);
  };
  const onClickCheckDuplicate = async (e) => {
    e.preventDefault();
    const request_data = {'email': email};
    try{
        let response = await axios({
                method: 'post',
                url: '/api/check-email-duplicate',
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify(request_data)
            });
            if(response.status >= 200 && response.status < 300){
                setMessage("사용가능한 이메일입니다!");
                setCheckEmailDuplicate(true);
            }
            else{
                setMessage("이미 존재하는 이메일입니다!");
                setEmail("");
            }
     } catch (err) {
        if(err.response.status === 409){ // CONFLICT
            setMessage("이미 존재하는 이메일입니다!");
            setEmail("");
        }
        else {
        console.log('onClickCheckDuplicate/err', err);
        }
     };

    };
  const onClickSignUpButton = (e) => {
    e.preventDefault();
    setLoginState(false);
  }
  const handleJoinSubmit = async (e) => {
    e.preventDefault();
    if(password !== confirmedPassword){
        alert("비밀번호와 재입력 비밀번호가 일치하지않습니다.");
        setPassword(""); setConfirmedPassword("");
        return;
    }
    if( !checkEmailDuplicate ){
        alert('이메일 중복검사를 해주시기 바랍니다');
        return;
    }
    // 성인인증
    const now = new Date();
    const now_year = now.getFullYear();
    const dayDiff = now_year - year;
    if(dayDiff < 20){
        alert("미성년자는 회원가입이 불가능합니다.")
        setYear(""); setMonth(""); setDay("")
    }
    setBirthday(year+'-'+month+'-'+day);
    const request_data = {'email': email, 'username': username, 'password': password, 'birthday': birthday};
    try {
        let response =
        await axios({
            method: 'post',
            url: '/api/join',
            headers: {'Content-Type': 'application/json'},
            data: JSON.stringify(request_data)
        });
        if(response.status >= 200 && response.status < 300){
            alert("회원가입이 완료되었습니다.");
            window.location.reload(); // 알림창에서 '확인'버튼 누르면 로그인 페이지로 redirect
        }
        else{
            alert("회원가입에 실패했습니다.");
            setEmail(""); setUsername(""); setYear(""); setMonth(""); setDay("");
            setPassword(""); setConfirmedPassword("");
        }
    } catch (err) {
        if(err.response.status >= 400){
           alert("회원가입에 실패했습니다.");
           setEmail(""); setUsername(""); setYear(""); setMonth(""); setDay("");
           setPassword(""); setConfirmedPassword("");
        }
        else{
            console.log('handleJoinSubmit/err: ', err);
        }
    }
  };
  const onClickAdult = (e) => {
    setBirthday(year+'-'+month+'-'+day);
    const request_data = {'email': email, 'username': username, 'password': password, 'birthday': birthday};
    const now = new Date();
    const now_year = now.getFullYear();
    const yearDiff = now_year - year;
    console.log(yearDiff);
    return;
  };

  return (
    <div className={styles.main}>
      <div className={styles.loginBox}>
        <div className={styles.imgDiv}>
          <img src="/img/logo.png" alt="네캔만원" className={styles.loginImg} />
        </div>
        {loginState === true ? ( // 로그인 화면
          <>
            <form action="/api/login" method="POST">
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
              <input type="email" className={styles.loginInput} name="email" />
              <label className={styles.label}>비밀번호</label>
              <input
                type="password"
                className={styles.passwordInput}
                name="password"
              />
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
            <form onSubmit={handleJoinSubmit}>
              <button
                className={styles.onClickButton}
                onClick={onClickSignUpButton}
              >
                회원가입
              </button>
              <button className={styles.Button} onClick={onClickLoginButton}>
                로그인
              </button>
              <div className={styles.emailCheckDiv}>
                <label className={styles.label}>이메일</label>
                <button className={styles.emailCheck} onClick={onClickCheckDuplicate} >중복검사</button>
              </div>
              <input type="email" className={styles.loginInput} name="email" value={email}
              onChange={(e) => setEmail(e.target.value)}/>
              <label className={styles.label} styles="color: red;">{message}</label><br/><br/>
              <label className={styles.label}>닉네임</label>
              <input
                type="text"
                className={styles.loginInput}
                name="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
              <label className={styles.label}>생년월일</label>
                <div className={styles.birthBox}>
                  <input
                    type="number"
                    className={styles.birthBoxInput}
                    name="year"
                    min="1000"
                    max="9999"
                    placeholder="년(4자)"
                    required
                    value={year}
                    onChange={(e) => setYear(e.target.value)}
                  />
                  <select
                    className={styles.birthBoxInput}
                    aria-label="월"
                    name="month"
                    required
                    value={month}
                    onChange={(e) => setMonth(e.target.value)}
                  >
                    <option value="" disabled selected>
                    월
                    </option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                  </select>
                  <input
                    type="text"
                    className={styles.birthBoxInput}
                    name="day"
                    maxLength="2"
                    placeholder="일"
                    required
                    value={day}
                    onChange={(e) => setDay(e.target.value)}
                  />
              </div>
              <label className={styles.label}>비밀번호</label>
              <input
                type="password"
                className={styles.loginInput}
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <label className={styles.label}>비밀번호 확인</label>
              <input
                type="password"
                className={styles.loginInput}
                name="passwordCheck"
                value={confirmedPassword}
                onChange={(e) => setConfirmedPassword(e.target.value)}
              />
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