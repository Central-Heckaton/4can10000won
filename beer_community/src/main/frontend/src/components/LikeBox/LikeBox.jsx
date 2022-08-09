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
            image={i.image_url}
            title={i.beer_name}
            description={i.information}
          />
        ))}
      </div>
    </>
  );
};

export default LikeBox;
