import React from "react";
import styles from "./likeBox.module.css";
import BeerBox from "../beerBox/beerBox";

const LikeBox = (props) => {
  console.log(props.data);
  return (
    <div className={styles.mainBox}>
      {Array.isArray(props.data) && props.data.length !== 0 ? (
        props.data.map((i) => (
          <BeerBox
            key={i.id}
            id={i.id}
            image={i.imageUrl}
            title={i.beerName}
            description={i.information}
            total_point={i.totalPoint}
          />
        ))
      ) : (
        <div className={styles.sub}>좋아요 한 맥주가 없어요</div>
      )}
    </div>
  );
};

export default LikeBox;
