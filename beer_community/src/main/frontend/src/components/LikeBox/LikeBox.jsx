import React from "react";
import styles from "./likeBox.module.css";
import BeerBox from "../beerBox/beerBox";

const LikeBox = (props) => {
  return (
    <>
      <div className={styles.mainBox}>
        {props.data.beer_list.map((i) => (
          <BeerBox
            key={i.id}
            id={i.id}
            image={i.imageUrl}
            title={i.beerName}
            description={i.information}
            total_point={i.totalPoint}
          />
        ))}
      </div>
    </>
  );
};

export default LikeBox;
