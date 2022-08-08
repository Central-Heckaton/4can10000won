import React from 'react';
import DetailBox from '../components/detailBox/detailBox';
import FilterBox from '../components/filterBox/filterBox';
import Footer from './../components/Footer/Footer';

const BeerType = (props) => {
    return (
        <div>
            <FilterBox />
            <DetailBox />
            <Footer />
        </div>
    );
};

export default BeerType;