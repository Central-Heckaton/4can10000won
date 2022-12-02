import React from "react";
import styles from "./likeBox.module.css";
import BeerBox from "../beerBox/beerBox";
import RandomLoading from "../randomLoading/RandomLoading";

const LikeBox = (props) => {
  console.log(props.data);
  return (
    <div className={styles.mainBox}>
      <div className={styles.button}>찜한 맥주 리스트</div>
      {props.loading ? (
        <>
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
            <div className={styles.sub}>찜한 맥주가 없어요</div>
          )}
        </>
      ) : (
        <>
          <RandomLoading />
        </>
      )}
    </div>
  );
};

export default LikeBox;
