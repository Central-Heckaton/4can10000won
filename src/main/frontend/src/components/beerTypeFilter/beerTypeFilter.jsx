import React from 'react';
import style from './beerTypeFilter.css';

const BeerTypeFilter = (props) => {

        const handleFilter = (e) => {
                const filter = e.target.dataset.filter;
                const beerInfo = document.querySelectorAll('.textBox');
                const active = document.querySelector('.box.selected');
                active.classList.remove('selected');
                e.target.classList.add('selected');

                beerInfo.forEach((beer) => {
                        if (filter === beer.dataset.type) {
                                beer.classList.remove('invisible');
                        } else {
                                beer.classList.add('invisible');
                        }
                });
        }

        return (        
                <div className='container'>
                        <div className='btnContainer'>
                                <button className='box selected'  data-filter="lager" onClick={handleFilter}>라거</button>
                                <button className='box'  data-filter="ale" onClick={handleFilter}>에일</button>
                                <button className='box'  data-filter="ipa" onClick={handleFilter}>IPA</button>
                                <button className='box' data-filter="stout" onClick={handleFilter}>스타우트</button>
                        </div>
                        <div className='beerContainer'>
                                <div className='textBox' data-type="lager">
                                        <p className='text'>
                                        국내에서 가장 쉽게 접할 수 있는 맥주입니다.
                                        라거는 ‘하면(下面)발효방식'이라 발효 중이나 끝난 후 효모가 아래로 가라앉으며 9~15℃의 저온에서 만듭니다. <br></br><br></br>
                                        효모와 부유단백질들이 바닥에 가라앉기 때문에
                                        유리잔에 따르면 맑은 황금색을 띄죠. 에일과 비교하였을 때 깔끔하고 청량감이 있습니다. <br></br><br></br>
                                        라거는 독일어로 ‘저장’을 뜻하는데요. 
                                        이는 에일보다 저장기간이 길었기 때문입니다. 
                                        하지만 19세기 냉장기술 발달과 저장기간을 단축시킬 수 있는 기술이 발달되면서 대량생산이 되기 시작합니다. <br></br><br></br>
                                        라거 맥주로는 필스너(Pilsener), 둥켈(Dunkel), 슈바르츠비어(Schwarzbier), 엑스포트(Export) , 버드와이저(Budweiser), 하이네켄(Heineken) , 기린 이치방 시보리(Kirin 一番搾り), 아사히 슈퍼 드라이(Asahi Super Dry) 등이 있습니다. <br></br><br></br>
                                        <span className='src'>출처 : 나무위키</span>
                                        </p>
                                </div>
                                <div className='textBox invisible' data-type="ale">
                                        <p className='text'>
                                        15 ~ 24 °C 의 상대적으로 높은 온도에서 발효시켜, 달콤하고 풀 바디감이 느껴지며 과일향이 있는 맥주의 한 종류입니다. 발효 시에 발생하는 이산화탄소가 효모에 달라붙어 표면으로 떠오르게 되고, 이로 인해 상면에 거품을 형성합니다. 따라서 상면발효 맥주라고도 합니다. <br></br><br></br>
                                        15°C 이하에서 저온발효시키는 라거에 비해 짧은 시간에 발효가 완성돼서, 발효 시작후 3주 이내에 마실 수 있는 상태가 됩니다. <br></br><br></br>
                                        라거가 가볍고 상쾌한 느낌이라면 에일은 색이 진하고 탄산이 적으며, 진하면서도 과일향이나 꽃향기와 같은 풍부한 향을 갖고 있는 것이 특징입니다. <br></br><br></br>
                                        전 세계적으로는 점유율 상 라거에 압도적으로 밀리고 있으나 영국에서는 Beer라는 표현 대신 Ale로 부를 만큼 대중적인 주종입니다. <br></br><br></br>
                                        <span className='src'>출처 : 나무위키</span>
                                        </p>
                                </div>
                                <div className='textBox invisible' data-type="ipa">
                                        <p className='text'>
                                        India Pale Ale. 줄여서 IPA라고도 많이 씁니다. 저장성 향상을 위해 알코올 도수와 홉 함량을 높인 에일입니다. <br></br><br></br>
                                        19세기에 영국의 식민지였던 인도에 거주하던 영국인들에게 주류를 수출 할 때 클리퍼로 아무리 빠르게 수송해도 2달이나 걸리는 배송시간 때문에 맥주가 상해버리는 문제가 발생했습니다. 이에 따라 인도 수출용으로는 저장성을 높인 고 도수 에일이 주로 유통되었고 이 에일 제품군이 "India Pale Ale"이라는 이름을 갖게 되었습니다. <br></br><br></br>
                                        <span className='src'>출처 : 나무위키</span>
                                        </p>
                                </div>
                                <div className='textBox invisible' data-type="stout">
                                        <p className='text'>
                                        검게 될 때까지 로스팅한 보리를 사용하여 표면 발효에 의해 양조되는 것을 가리킵니다. 보통 흑맥주라고도 부릅니다. <br></br><br></br>
                                        <span className='src'>출처 : 위키백과</span>
                                        </p>
                                </div>
                        </div>
                </div>
        );
}

export default BeerTypeFilter;