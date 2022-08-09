import React from 'react';
import DetailFooter from '../components/detailFooter/detailFooter';
import DetailImgs from '../components/detailImgs/detailImgs';
import DetailInfo from '../components/detailInfo/detailInfo';
import DetailText from './../components/detailText/detailText';

const Detail = (props) => {
    return (
        <div>
            <DetailImgs />
            <DetailInfo />
            <DetailText />
            <DetailFooter />
        </div>
    );
};

export default Detail;