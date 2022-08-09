import React from 'react';
import styles from './beerSearch.module.css'

const BeerSearch = (props) => {
    return (
        <div className={styles.container}>
            <input className={styles.input} type="text" />
            <button className={styles.search}>
                <img src="/img/search.png" alt="search img" />
            </button>
        </div>
    );
};

export default BeerSearch;