import React from "react";
import DetailDegree from "../detailDegree/detailDegree";
import DetailTaste from "../detailTaste/detailTaste";
import styles from "./detailInfo.module.css";

const DetailInfo = (props) => {
  return (
    <div className={styles.container}>
      <DetailDegree alcoholDegree={props.alcoholDegree} />
      <DetailTaste taste={props.taste} totalPoint={props.totalPoint} />
    </div>
  );
};

export default DetailInfo;
