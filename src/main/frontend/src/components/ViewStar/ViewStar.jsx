import React, { useEffect, useRef } from "react";
import styles from "./viewStar.module.css";
const ViewStar = (props) => {
  const realStar = useRef();
  //   const [score, setScore] = useState("");
  useEffect(() => {
    const score = (props.point / 5) * 100;
    realStar.current.style = `width:${score}%;`;
  });

  return (
    <div className={styles.mainBox}>
      <div className={styles.fakeStar}>
        <img className={styles.star} src="/img/star-regular.png" alt="" />
        <img className={styles.star} src="/img/star-regular.png" alt="" />
        <img className={styles.star} src="/img/star-regular.png" alt="" />
        <img className={styles.star} src="/img/star-regular.png" alt="" />
        <img className={styles.star} src="/img/star-regular.png" alt="" />
      </div>
      <div className={styles.realStar} ref={realStar}>
        <img className={styles.star} src="/img/star.png" alt="" />
        <img className={styles.star} src="/img/star.png" alt="" />
        <img className={styles.star} src="/img/star.png" alt="" />
        <img className={styles.star} src="/img/star.png" alt="" />
        <img className={styles.star} src="/img/star.png" alt="" />
      </div>
    </div>
  );
};

export default ViewStar;
