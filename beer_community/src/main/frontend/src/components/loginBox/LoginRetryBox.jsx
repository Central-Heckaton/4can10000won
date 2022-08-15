import React, { useState, useRef, useEffect } from "react";
import styles from "./loginBox.module.css";
import axios from "axios";

const LoginRetryBox = () => {
  const [loginState, setLoginState] = useState(true);
  const [message, setMessage] = useState("");
  const [checkDuplicate, setCheckDuplicate] = useState(); // 초기값을 안 줌으로써 checkDuplicate
  // -- 회원가입시 사용할 데이터 --
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmedPassword, setConfirmedPassword] = useState("");
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [day, setDay] = useState("");
  const [birthday, setBirthday] = useState("");

  const emailMessage = useRef();
  // ---------

  const resetInput = () => {
    setEmail("");
    setUsername("");
    setYear("");
    setMonth("");
    setDay("");
    setPassword("");
    setMessage("");
    setCheckDuplicate();
    setConfirmedPassword("");
  };
  const onClickLoginButton = (e) => {
    e.preventDefault();
    resetInput();
    emailMessage.current.style = "color:black;";
    setLoginState(true);
  };
  const onClickSignUpButton = (e) => {
    e.preventDefault();
    resetInput();
    setLoginState(false);
  };
  function changeMessageColor(checkDuplicate) {
    if (checkDuplicate === true) {
      emailMessage.current.style = "color:blue;";
    } else if (checkDuplicate === false) {
      emailMessage.current.style = "color:red;";
    }
    return;
  }
  const onClickCheckDuplicate = async (e) => {
    e.preventDefault();
    console.log("Clicked")
    const request_data = { email: email }; // type of -> Object
    if (email.length === 0) {
      alert("이메일을 입력해주시기 바랍니다.");
      setCheckDuplicate(false);
      return;
    }
    try {
      let response = await axios({
        method: "post",
        url: "/api/check-email-duplicate",
        headers: { "Content-Type": "application/json" },
        data: JSON.stringify(request_data),
      });
      if (response.status >= 200 && response.status < 300) {
        setCheckDuplicate(true);
        setMessage("사용가능한 이메일입니다!");
      } else {
        setCheckDuplicate(false);
        setMessage("이미 존재하는 이메일입니다!");
        setEmail("");
      }
    } catch (err) {
      console.log("err.response.data[\"error\"] : ", err.response.data["error"])
      if (err.response.status === 400){  //이메일 중복검사 시 양식이 올바르지 않은 경우
        setCheckDuplicate(false);
        setMessage(err.response.data["error"]);
        setEmail("")
      }
      else if (err.response.status === 409) {
        // CONFLICT
        setCheckDuplicate(false);
        setMessage("이미 존재하는 이메일입니다!");
        setEmail("");
      } else {
        console.log("onClickCheckDuplicate/err", err);
      }
    }
  };

  const handleJoinSubmit = async (e) => {
    e.preventDefault();
    if (password !== confirmedPassword) {
      alert("비밀번호와 재입력 비밀번호가 일치하지않습니다.");
      setPassword("");
      setConfirmedPassword("");
      return;
    }
    if (!checkDuplicate) {
      alert("이메일 중복검사를 해주시기 바랍니다");
      return;
    }
    // 성인인증
    const now = new Date();
    const now_year = now.getFullYear();
    const dayDiff = now_year - year;
    if (dayDiff < 20) {
      alert("미성년자는 회원가입이 불가능합니다.");
      setYear("");
      setMonth("");
      setDay("");
      return;
    }
    setBirthday(year + "-" + month + "-" + day);
    const request_data = {
      email: email,
      username: username,
      password: password,
      birthday: birthday,
    };
    try {
      let response = await axios({
        method: "post",
        url: "/api/join",
        headers: { "Content-Type": "application/json" },
        data: JSON.stringify(request_data),
      });
      if (response.status >= 200 && response.status < 300) {
        alert("회원가입이 완료되었습니다.");
        resetInput();
        emailMessage.current.style = "color:black;";
        setLoginState(true);
        // 알림창에서 '확인'버튼 누르면 로그인 페이지로 redirect
      } else {
        alert("회원가입에 실패했습니다.");
        resetInput();
      }
    } catch (err) {
      if (err.response.status >= 400) {
        console.log("err/message : " + err.response.data["error"])
        if(err.response.data["error"] !== ""){  //이메일 형식에 맞지 않을 경우
          alert(err.response.data["error"])
        }
        else{
          alert("회원 가입에 실패했습니다.")
        }
        resetInput();
      } else {
        console.log("handleJoinSubmit/err: ", err);
      }
    }
  };
  const onCheckEnter = (e) => {
    //여기다 onSubmit에 들어갈 함수 넣어야한다.
    if (e.keyCode === 13) {
      e.preventDefault();
      document.loginForm.submit();
    }
  };
  // checkDuplicate 값이 변경될때 마다 useEffect 실행됨
  useEffect(() => {
   alert("로그인에 실패하였습니다.");
    try {
      changeMessageColor(checkDuplicate);
    } catch (err) {
      console.log(err);
    }
    return;
  }, [checkDuplicate]);
  return (
    <div className={styles.main}>
      <div className={styles.loginBox}>
        <div className={styles.imgDiv}>
          <img src="/img/logo.png" alt="네캔만원" className={styles.loginImg} />
        </div>
        {loginState === true ? ( // 로그인 화면
          <>
            <form
              action="/api/login"
              method="POST"
              onSubmit={() => {
                console.log("login");
              }}
              onKeyDown={onCheckEnter}
              name="loginForm"
            >
              <button className={styles.Button} onClick={onClickSignUpButton}>
                회원가입
              </button>
              <button
                className={styles.onClickButton}
                onClick={(e) => {
                  e.preventDefault();
                }}
              >
                로그인
              </button>
              <label className={styles.label}>이메일</label>
              <input
                type="email"
                required
                className={styles.loginInput}
                name="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <label className={styles.label}>비밀번호</label>
              <input
                type="password"
                required
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
              <a
                href="http://localhost:8080/oauth2/authorization/naver"
                className={[styles.loginAnotherButton, styles.naver].join(" ")}
              > </a>
              <a
                href="http://localhost:8080/oauth2/authorization/google"
                className={[styles.loginAnotherButton, styles.google].join(" ")}
              > </a>
            </div>
          </>
        ) : (
          // 회원가입 화면
          <>
            <form onSubmit={handleJoinSubmit}>
              <button
                className={styles.onClickButton}
                onClick={(e) => {
                  e.preventDefault();
                }}
              >
                회원가입
              </button>
              <button className={styles.Button} onClick={onClickLoginButton}>
                로그인
              </button>
              <div className={styles.emailCheckDiv}>
                <label className={styles.label}>이메일</label>
                <button
                  className={styles.emailCheck}
                  onClick={onClickCheckDuplicate}
                >
                  중복검사
                </button>
              </div>
              <input
                type="email"
                className={styles.emailInput}
                name="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <label className={styles.dupEmail} ref={emailMessage}>
                {message}
              </label>
              <br />
              <br />
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

export default LoginRetryBox;
