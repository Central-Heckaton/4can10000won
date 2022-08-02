import React from "react";
import styles from "./footer.module.css";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faMagnifyingGlass,
  faEllipsis,
} from "@fortawesome/free-solid-svg-icons";
import { faHeart, faUser } from "@fortawesome/free-regular-svg-icons";
const Footer = () => {
  return (
    <footer className={styles.main}>
      <Link to="/" className={styles.small}>
        <FontAwesomeIcon
          className={styles.smallImg}
          icon={faMagnifyingGlass}
          size="2x"
        ></FontAwesomeIcon>
        <div className={styles.text}>SEARCH</div>
      </Link>
      <Link to="/" className={styles.small}>
        <FontAwesomeIcon
          className={styles.smallImg}
          icon={faHeart}
          size="2x"
        ></FontAwesomeIcon>
        <div className={styles.text}>LIKE</div>
      </Link>
      <Link to="/profile" className={styles.small}>
        <FontAwesomeIcon
          className={styles.smallImg}
          icon={faUser}
          size="2x"
        ></FontAwesomeIcon>
        <div className={styles.text}>PROFILE</div>
      </Link>
      <Link to="/menu" className={styles.small}>
        <FontAwesomeIcon
          className={styles.smallImg}
          icon={faEllipsis}
          size="2x"
        ></FontAwesomeIcon>
        <div className={styles.text}>MENU</div>
      </Link>
    </footer>
  );
};

export default Footer;
