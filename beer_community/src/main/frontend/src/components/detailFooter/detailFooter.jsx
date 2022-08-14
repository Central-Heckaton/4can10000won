import React from "react";
import styles from "./detailFooter.module.css";
import { useState, useEffect } from "react";
import { axios } from "axios";
import { useLocation, Link } from "react-router-dom";


const DetailFooter = (props) => {
  const [like, setLike] = useState(null);
  const location = useLocation();
  const id = 1;
//  const id = location.state.id;
//  console.log("props:", props);
  useEffect(() => {
    if (like === null) {
//      setLike(props.isLiked);
      setLike(false);
      console.log(like);
      return;
    }
    const putLike = async () => {
      const request_data = { beerId: id, state: like};
      console.log('id: ', id, 'like: ', like);
      try{
        var url = "/api/like-beer/";
        console.log('url: ', url);
        // `/api/beer-like/${id}/${like ? 1 : 0}`
//        let response = await axios.get(url);
        let response = await axios({
            method: 'post',
            url: url,
            headers: {'Content-Type': 'application/json'},
            data: JSON.stringify(request_data)
        })
        console.log('response: ', response);
      }
      catch (err) {
        console.log('err: ', err);
      }

    };
    putLike();
  }, [like]);

  return (
    <div className={styles.container}>
      <div className={styles.goReview}>
        <p className={styles.title}>리뷰</p>
        <p className={styles.reviewNum}>(800)</p>
      </div>
      <div className={styles.goRate}>
<<<<<<< Updated upstream
        <Link
          to={"/rate"}
          className={styles.link}
          state={{
            id: props.id,
          }}
        >
          <button className={styles.button}>나도 평가하기</button>
        </Link>
=======
        <button className={styles.button}>나도 평가하기</button>
>>>>>>> Stashed changes
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
