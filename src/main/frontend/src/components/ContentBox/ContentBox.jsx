import React from "react";
import styles from "./contentBox.module.css";
const ContentBox = () => {
  return (
    <>
      <div className={styles.main}>
        <div className={styles.button}>캔맥주 더 맛있게 먹는 방법</div>
        <div className={styles.textBox}>
          <img
            className={styles.mainImg}
            src="/img/howtodrink.png"
            alt="howtodrink"
          />
        </div>
      </div>
    </>
  );
};

export default ContentBox;
