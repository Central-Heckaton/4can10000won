// detailFooter.jsx
import React from "react";
import styles from "./detailFooter.module.css";
import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

const DetailFooter = (props) => {
  const [count, setCount] = useState(0);
  const [like, setLike] = useState("");
  useEffect(() => {
    console.log("useEffect setlike call");
    setLike(props.isLiked);
  }, [props.isLiked]);
  useEffect(() => {
    console.log("propslike:", props.isLiked);
    console.log("like:", like);
    const putLike = async () => {
      const request_data = { beerId: props.id, state: like };
      console.log("request data: ", request_data);
      try {
        let response = await axios({
          method: "post",
          url: "/api/like-beer/",
          headers: { "Content-Type": "application/json" },
          data: JSON.stringify(request_data),
        });
        console.log("response: ", response);
      } catch (err) {
        console.log("err: ", err);
      }
    };
    if (count > 1) {
      putLike();
      console.log("22:", count);
    } else {
      setCount(count + 1);
      console.log("11:", count);
    }
  }, [like]);

  const heartOnclick = async (e) => {
    e.preventDefault();
    console.log("click like: ", like);
    if (props.loginState === false) {
      //로그아웃 상태일때
      if (
        window.confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?")
      ) {
        props.navigate("/login");
      }
    } else {
      //로그인 상태일때
      await setLike(!like);
    }
  };
  const rateOnclick = (e) => {
    if (props.loginState === false) {
      if (
        window.confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?")
      ) {
        e.preventDefault();
        props.navigate("/login");
      } else {
        e.preventDefault();
      }
    }
  };
  return (
    <div className={styles.container}>
      <Link
        to={"/review"}
        className={styles.link}
        state={{
          id: props.id,
        }}
      >
        <div className={styles.goReview}>
          <p className={styles.title}>리뷰</p>
          <p className={styles.reviewNum}>({props.reviewCount})</p>
        </div>
      </Link>
      <div className={styles.goRate}>
        <Link
          to={"/rate"}
          className={styles.link}
          state={{
            id: props.id,
          }}
          onClick={rateOnclick}
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
            onClick={heartOnclick}
          />
        </button>
      </div>
    </div>
  );
};

export default DetailFooter;
