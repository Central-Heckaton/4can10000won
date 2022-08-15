import React from "react";
import styles from "./detailFooter.module.css";
import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

const DetailFooter = (props) => {
  const [count, setCount] = useState(0);
  const [like, setLike] = useState(props.isLiked);
  useEffect(() => {
 
    const putLike = async () => {
      const request_data = { beerId: props.id, state: like };
      try {
        let response = await axios({
          method: "post",
          url: "/api/like-beer",
          headers: { "Content-Type": "application/json" },
          data: JSON.stringify(request_data),
        });
        console.log("response: ", response);
      } catch (err) {
        console.log("err: ", err);
      }
    };
    if (count > 0) {
      console.log('like: ',like)
      putLike();
    } else if (count === 0) {
      setCount(1);
    }
  }, [like]);

  return (
    <div className={styles.container}>
      <div className={styles.goReview}>
        <p className={styles.title}>리뷰</p>
        <p className={styles.reviewNum}>(800)</p>
      </div>
      <div className={styles.goRate}>
        <Link
          to={"/rate"}
          className={styles.link}
          state={{
            id: props.id,
          }}
        >
          <button className={styles.button}>나도 평가하기</button>
        </Link>
      </div>
      <div className={styles.like}>
        <button className={styles.heart}>
          <img
            className={styles.heartReg}
            src={like ? "/img/heart-solid.png" : "/img/heart-regular.png"}
            alt="heart"
            onClick={() => {
              setLike(!like);
            }}
          />
        </button>
      </div>
    </div>
  );
};

export default DetailFooter;
