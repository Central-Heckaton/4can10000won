import React from "react";
import styles from "./detailTaste.module.css";
import ViewStar from "../ViewStar/ViewStar";

const DetailTaste = (props) => {
  console.log("taste : ", props.taste);
  return (
    <div className={styles.container}>
      <div className={styles.titleBox}>
        <p className={styles.title}>Taste</p>
        {Array.isArray(props.taste) &&
          props.taste.map((i,index) => {
            // SWEET, SOUR, BITTER, FLORAL, AROMATIC, FRUITINESS
            // 달콤함, 새콤함, 씁쓸함, 꽃향기, 고소함, 과일향
            if (i === "SWEET") {
              return (
                <button key={index} className={styles.tag1}>
                  달콤함
                </button>
              );
            } else if (i === "SOUR") {
              return (
                <button key={index} className={styles.tag1}>
                  신맛
                </button>
              );
            } else if (i === "BITTER") {
              return (
                <button key={index} className={styles.tag1}>
                  씁쓸함
                </button>
              );
            } else if (i === "FLORAL") {
              return (
                <button key={index} className={styles.tag1}>
                  꽃향기
                </button>
              );
            } else if (i === "AROMATIC") {
              return (
                <button key={index} className={styles.tag1}>
                  고소함
                </button>
              );
            } else if (i === "FRUITINESS") {
              return (
                <button key={index} className={styles.tag1}>
                  과일향
                </button>
              );
            }
          })}
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
