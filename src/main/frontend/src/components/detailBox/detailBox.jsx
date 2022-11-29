import React from 'react';
import styles from './detailBox.module.css'

const DetailBox = (props) => {
    return (
        <div className={styles.container}>
            <div className={styles.box}>
                <p className={styles.text}>
                    국내에서 가장 쉽게 접할 수 있는 맥주입니다.
                    라거는 ‘하면(下面)발효방식'이라 발효 중이나 끝난 후 효모가 아래로 가라앉으며 9~15℃의 저온에서 만듭니다. <br></br><br></br>
                    효모와 부유단백질들이 바닥에 가라앉기 때문에
                    유리잔에 따르면 맑은 황금색을 띄죠. 에일과 비교하였을 때 깔끔하고 청량감이 있습니다. <br></br><br></br>
                    라거는 독일어로 ‘저장’을 뜻하는데요. 
                    이는 에일보다 저장기간이 길었기 때문입니다. 
                    하지만 19세기 냉장기술 발달과 저장기간을 단축시킬 수 있는 기술이 발달되면서 대량생산이 되기 시작합니다. <br></br><br></br>
                    라거 맥주로는 필스너(Pilsener), 둥켈(Dunkel), 슈바르츠비어(Schwarzbier), 엑스포트(Export) , 버드와이저(Budweiser), 하이네켄(Heineken) , 기린 이치방 시보리(Kirin 一番搾り), 아사히 슈퍼 드라이(Asahi Super Dry) 등이 있습니다.
                </p>
            </div>
        </div>
    );
};

export default DetailBox;