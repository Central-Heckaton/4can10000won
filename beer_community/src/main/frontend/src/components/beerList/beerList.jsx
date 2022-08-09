import React from "react";
import BeerBox from "../beerBox/beerBox";
import styles from "./beerList.module.css";
const BeerList = (props) => {
  return (
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
  );
};

export default BeerList;
