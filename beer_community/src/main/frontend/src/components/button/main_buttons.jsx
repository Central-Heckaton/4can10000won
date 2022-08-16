import React from "react";
import styles from "./main_buttons.module.css";
import { Link } from "react-router-dom";

const MainButtons = (props) => (
  <div className={styles.container}>
    <Link to={"/search"}>
      <button className={styles.button}>편의점 맥주 검색 🔍</button>
    </Link>
    <Link to={"/random"}>
      <button className={styles.button}>맥주 랜덤 추첨 🎰</button>
    </Link>

    <button className={styles.button}>캔맥주 더 맛있게 먹는 법 🍺</button>

    <Link to={"/beertype"}>
      <button className={styles.button}>맥주 종류를 잘 모른다면? 🙌</button>
    </Link>
  </div>
);

export default MainButtons;
