import React from "react";

import styles from "./randomBox.module.css";
const RandomBox = (props) => {
  return (
    <div className={styles.mainBox}>
      <div className={styles.title}>오늘의 맥주는?</div>
      <img src={props.data.image_url} alt="" className={styles.img} />
      <div className={styles.detail}>
        <div className={styles.degree}>
          <p className={styles.text}>Alchol degree</p>
          <p className={styles.degreeNum}>{props.data.alcohol_degree}</p>
        </div>
        
      </div>

      <div className={styles.name}>{props.data.beer_name}</div>
      <div className={styles.description}>
        {props.data.imformation}
      </div>
      <button className={styles.button}>
        다시하기
      </button>
    </div>
  );
};

export default RandomBox;
