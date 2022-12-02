import React from "react";
import ViewStar from "../ViewStar/ViewStar";
import styles from "./beerBox.module.css";
import { Link } from "react-router-dom";

const BeerBox = (props) => {
  return (
    <>
      <Link
        to={"/detail"}
        state={{
          id: props.id,
        }}
      >
        <div className={styles.box}>
          <img className={styles.img} src={props.image} alt="" />
          <div className={styles.text}>
            <h1 className={styles.name}>{props.title}</h1>
            <ViewStar point={props.total_point} />
            <div className={styles.description}>{props.description}</div>
          </div>
        </div>
      </Link>
    </>
  );
};
export default BeerBox;
