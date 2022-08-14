import React from "react";
import styles from "./detailTaste.module.css";
import ViewStar from "../ViewStar/ViewStar";

const DetailTaste = (props) => {
  return (
    <div className={styles.container}>
      <div className={styles.titleBox}>
        <p className={styles.title}>Taste</p>
        <button className={styles.tag1}>부드러움</button>
        <button className={styles.tag2}>과일향</button>
      </div>
      <div className={styles.totalBox}>
        <p className={styles.total}>총점</p>
        <ViewStar point={props.totalPoint} />
        <p className={styles.rateNum}>({props.totalPoint})</p>
      </div>
    </div>
  );
};

export default DetailTaste;
