import React from "react";
import styles from "./nav.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft } from "@fortawesome/free-solid-svg-icons";
const Nav = (props) => {
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
        </div>
      </div>
    </>
  );
};

export default Nav;
