import React from "react";
import BeerBox from "../beerBox/beerBox";
import styles from "./beerList.module.css";
const BeerList = (props) => {
  return (
    <div className={styles.mainBox}>
      {Array.isArray(props.data) ?
        props.data.map((i) => (
        <BeerBox
          key={i.id}
          id={i.id}
          image={i.imageUrl}
          title={i.beerName}
          description={i.information}
          total_point={i.total_point}
        />
      ))
      : null}
    </div>
  );
};

export default BeerList;
