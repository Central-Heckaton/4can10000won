import React from "react";
import styles from "./detailFooter.module.css";
import { useState, useEffect } from "react";
import { axios } from "axios";
import { useLocation, Link } from "react-router-dom";

const DetailFooter = (props) => {
  const [like, setLike] = useState(null);
  const location = useLocation();
  const id = location.state.id;
  console.log("props:", props);
  useEffect(() => {
    if (like === null) {
      setLike(props.isLiked);
      console.log(like);
      return;
    }
    // const putLike = async () => {
    //   const request_data = { beerId: id, state: like ? 1 : 0 };

    //   await axios({
    //     method: "post",
    //     url: `/api/beer-like/${id}/${like ? 1 : 0}`,
    //     headers: { "Content-Type": "application/json" },
    //     data: JSON.stringify(request_data),
    //   }).catch((error) => console.log("error :", error));
    // };
    // putLike();
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
              console.log(like);
            }}
          />
        </button>
      </div>
    </div>
  );
};

export default DetailFooter;
