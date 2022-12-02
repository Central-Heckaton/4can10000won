import React, { useState } from 'react';
import styles from './beerSearch.module.css'
import axios from 'axios';

const BeerSearch = (props) => {
    const [beerName, setBeerName] = useState();
    const handleSearchBtnClick = async (e) => {
        e.preventDefault();
        console.log('beerSearch.jsx/clicked!');
        let url = "/api/beername-search/" + beerName
        console.log('url: ', url);
        let response = await axios.get(url);
        console.log('response: ', response);
    }
    return (
        <div className={styles.searchContainer}>
            <input
                name="beerName"
                className={styles.input}
                type="text"
                value={beerName}
                onChange={(e) => setBeerName(e.target.value)}
             />
            <button className={styles.search} onClick={handleSearchBtnClick}>
                <img src="/img/search.png" alt="search img" />
            </button>
        </div>
    );
};

export default BeerSearch;