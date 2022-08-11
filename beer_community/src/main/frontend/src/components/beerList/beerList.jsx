import React, {useState} from "react";
import BeerBox from "../beerBox/beerBox";
import styles from "./beerList.module.css";
const BeerList = (props) => {
    console.log('beerList.jsx');
   const[clickedId, setClickedId] = useState()
   const handleDetail = (e) => {
        setClickedId(e.target.key);
        console.log('id: ', clickedId);
   }
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
          total_point={i.totalPoint}
        />
      ))
      : null}
    </div>
  );
};

export default BeerList;
