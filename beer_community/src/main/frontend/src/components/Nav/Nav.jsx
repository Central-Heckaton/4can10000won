import React from "react";
import styles from "./nav.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft } from "@fortawesome/free-solid-svg-icons";
import { faArrowRightToBracket } from "@fortawesome/free-solid-svg-icons";
import { faArrowRightFromBracket } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import axios from "axios";

const Nav = (props) => {
  const [loginState, setLoginState] = useState(true);
  console.log('hihi');
  return (
    <>
      <div className={styles.main}>
        <div className={styles.back}>
          <FontAwesomeIcon
            icon={faAngleLeft}
            size="1x"
            onClick={() => {
              props.navigate(-1);
            }}
          />
          <FontAwesomeIcon
            icon={loginState===true?faArrowRightToBracket:faArrowRightFromBracket}
            size="1x"
            onClick={async () => {
               // 지금은 로그아웃만 있다
              let response = await axios.get("/api/logout");
              console.log("Nav/response: ", response);
//              props.navigate("/login");
            }}
          />
        </div>
      </div>
    </>
  );
};

export default Nav;
