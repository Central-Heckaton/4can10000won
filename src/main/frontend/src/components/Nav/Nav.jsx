import React, { useState } from "react";
import styles from "./nav.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft } from "@fortawesome/free-solid-svg-icons";
import { faArrowRightToBracket } from "@fortawesome/free-solid-svg-icons";
import { faArrowRightFromBracket } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import useLoginState from "../../hooks/useLoginState";
import { useEffect } from "react";

const Nav = ({ navigate }) => {
  const loginstate = useLoginState();
  console.log("loginstate", loginstate);
  return (
    <>
      <div className={styles.main}>
        <div className={styles.back}>
          <FontAwesomeIcon
            icon={faAngleLeft}
            size="1x"
            onClick={() => {
              navigate(-1);
            }}
          />
          <FontAwesomeIcon
            icon={
              loginstate === null
                ? null
                : loginstate === false
                ? faArrowRightToBracket
                : faArrowRightFromBracket
            }
            size="1x"
            onClick={async () => {
              if (loginstate === true) {
                await axios.get("/api/logout");
                alert("로그아웃되었습니다!");
                navigate("/search");
              } else if (loginstate === false) {
                navigate("/login");
              }
            }}
          />
        </div>
      </div>
    </>
  );
};
Nav.defaultProps = {};
export default Nav;
