import React from 'react';
import ViewStar from '../ViewStar/ViewStar';
import styles from './beerBox.module.css'

const BeerBox = (props) => {
    return (
        <div className={styles.box}>
            <img className={styles.img} src={props.image} alt="" />
            <div className={styles.text}>
                <h1 className={styles.name}>{props.title}</h1>
                <ViewStar point={props.total_point}/>
                <div className={styles.description}>
                    {props.description}
                </div>
            </div>
        </div>
    );
};
export default BeerBox;