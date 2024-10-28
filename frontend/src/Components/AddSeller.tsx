// import '@mantine/core/styles.css';
// // @ts-ignore
// import {TextInput, NumberInput, Button, Checkbox} from '@mantine/core';
// import {useState} from 'react';
// import axios from 'axios';
//
// const BACK_URL = import.meta.env.VITE_BACK_URL;
//
// function AddPrice() {
//     console.log('Component rendered');
//
//     const [value, setValue] = useState('');
//     const [value1, setValue1] = useState<string | number>('');
//     const [dateResponse, setDateResponse] = useState('');
//
//     const [priceName, setPriceName] = useState('');
//     const [pathToPrice, setPathToPrice] = useState('');
//     const [currencyCode, setCurrencyCode] = useState<number | string>('');
//     const [vatRate, setVatRate] = useState<number | string>('');
//     const [currencyBankCoeff, setCurrencyBankCoeff] = useState<number | string>('');
//     const [coeffDeliveryCost, setCoeffDeliveryCost] = useState<number | string>('');
//     const [minOrderSum, setMinOrderSum] = useState<number | string>('');
//
//
//     const [brandColNumber, setBrandColNumber] = useState<number | string>('');
//     const [articleColNumber, setArticleColNumber] = useState<number | string>('');
//     const [productCategoryColNumber, setProductCategoryColNumber] = useState<number | string>('');
//     const [productNameColNumber, setProductNameColNumber] = useState<number | string>('');
//     const [priceColNumber, setPriceColNumber] = useState<number | string>('');
//     const [onStockColNumber, setOnStockColNumber] = useState<number | string>('');
//     const [barcodeColNumber, setBarcodeColNumber] = useState<number | string>('');
//     const [tnvedColNumber, setTnvedColNumber] = useState<number | string>('');
//
//     const [ownPriceColNumber, setOwnPriceColNumber] = useState<number | string>('');
//     const [ownOnStockColNumber, setOwnOnStockColNumber] = useState<number | string>('');
//     const [ownReservedOnStockColNumber, setOwnReservedOnStockColNumber] = useState<number | string>('');
//     const [ownFreeOnStockColNumber, setOwnFreeOnStockColNumber] = useState<number | string>('');
//     const [ownPriceForSiteColNumber, setOwnPriceForSiteColNumber] = useState<number | string>('');
//
//     // const [color, setColor] = useState('');
//     // const [upholstery, setUpholstery] = useState('');
//     // const [hasAirConditioning, setHasAirConditioning] = useState(false);
//
//     const handleSubmit = async (event: any) => {
//         event.preventDefault();
//
//         const response = await axios.post(`${BACK_URL}/api/currency`, {
//             value,
//             value1,
//             seller: {
//                 priceName,
//                 pathToPrice,
//                 currencyCode,
//                 vatRate,
//                 currencyBankCoeff,
//                 coeffDeliveryCost,
//                 minOrderSum
//
//             },
//             priceDetails: {
//
//                 brandColNumber,
//                 articleColNumber,
//                 productCategoryColNumber,
//                 productNameColNumber,
//                 priceColNumber,
//                 onStockColNumber,
//                 barcodeColNumber,
//                 tnvedColNumber,
//                 // color,
//                 // upholstery,
//                 // hasAirConditioning
//             },
//             ownPriceDetails: {
//                 ownPriceColNumber,
//                 ownOnStockColNumber,
//                 ownReservedOnStockColNumber,
//                 ownFreeOnStockColNumber,
//                 ownPriceForSiteColNumber
//             }
//         }, {
//             headers: {
//                 'Content-Type': 'application/json',
//             }
//         });
//
//         const data = await response.data;
//         console.log(data);
//     };
//
//     const handleDateSubmit = async () => {
//         const currentDate = new Date().toISOString();
//
//         const response = await axios.post(`${BACK_URL}/api/nbcurrency`, {date: currentDate}, {
//             headers: {
//                 'Content-Type': 'application/json',
//             }
//         });
//
//         const data = await response.data;
//         setDateResponse(data);
//     };
//
//     const saveData = async () => {
//
//         // const response = await axios.post(`${BACK_URL}/api/addPrice`,
//         const response = await axios.post(`${BACK_URL}/api/addSeller`,
//             {
//                 priceName: priceName,
//                 articleColNumber: articleColNumber
//
//             }, {
//                 headers: {
//                     'Content-Type': 'application/json',
//                 }
//             }
//         );
//
//         const data = await response.data;
//         setDateResponse(data);
//     };
//
//     const handleReset = () => {
//         setValue('');
//         setValue1('');
//         setPriceName('');
//         setPathToPrice('');
//         setCurrencyCode('');
//         setVatRate('');
//         setCurrencyBankCoeff('');
//         setCoeffDeliveryCost('');
//         setMinOrderSum('');
//
//
//         setBrandColNumber('');
//         setArticleColNumber('');
//         setProductCategoryColNumber('');
//         setProductNameColNumber('');
//         setPriceColNumber('');
//         setOnStockColNumber('');
//         setBarcodeColNumber('');
//         setTnvedColNumber('');
//
//
//         setOwnPriceColNumber('');
//         setOwnOnStockColNumber('');
//         setOwnReservedOnStockColNumber('');
//         setOwnFreeOnStockColNumber('');
//         setOwnPriceForSiteColNumber('');
//
//         // setHasAirConditioning(false);
//     };
//
//     return (
//         <div>
//             <h2>Настройка для парсинга собственного прайса</h2>
//             <form onSubmit={handleSubmit}
//                   style={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start', gap: '1em'}}>
//                 <div style={{display: 'flex', gap: '2em'}}>
//                     <div>
//                         <h3>Сведения о прайсе продавца</h3>
//                         <TextInput value={priceName} onChange={(event) => setPriceName(event.currentTarget.value)}
//                                    placeholder="Название прайса" required/>
//                         <TextInput value={pathToPrice} onChange={(event) => setPathToPrice(event.currentTarget.value)}
//                                    placeholder="Ссылка на прайс" required/>
//                         <NumberInput value={currencyCode} onChange={setCurrencyCode} placeholder="Код валюты" required/>
//                         <NumberInput value={vatRate} onChange={setVatRate} placeholder="Учтенный % НДС" required/>
//                         <NumberInput value={currencyBankCoeff} onChange={setCurrencyBankCoeff}
//                                      placeholder="Коэфициент банковских потерь" required/>
//                         <NumberInput value={coeffDeliveryCost} onChange={setCoeffDeliveryCost}
//                                      placeholder="Коэфициент стоимости доставки" required/>
//                         <NumberInput value={minOrderSum} onChange={setMinOrderSum}
//                                      placeholder="Минимальная сумма заказа" required/>
//                     </div>
//                     <div>
//                         <h3>Номера столбцов для парсинга</h3>
//                         <NumberInput value={brandColNumber} onChange={setBrandColNumber} placeholder="Бренд, столбец №"
//                                      required/>
//                         <NumberInput value={articleColNumber} onChange={setArticleColNumber}
//                                      placeholder="Артикул, столбец №" required/>
//                         <NumberInput
//                             value={productCategoryColNumber}
//                             onChange={setProductCategoryColNumber}
//                             placeholder="Категория, столбец №"
//                             // required
//                         />
//                         <NumberInput value={productNameColNumber} onChange={setProductNameColNumber}
//                                      placeholder="Название, столбец №"
//                                      required
//                         />
//                         <NumberInput value={priceColNumber} onChange={setPriceColNumber} placeholder="Цена, столбец №" required/>
//                         <NumberInput value={onStockColNumber} onChange={setOnStockColNumber}
//                                      placeholder="Наличие, столбец №"
//                             // required
//                         />
//                         <NumberInput value={barcodeColNumber} onChange={setBarcodeColNumber}
//                                      placeholder="Баркод, столбец №"
//                             // required
//                         />
//                         <NumberInput value={tnvedColNumber} onChange={setTnvedColNumber} placeholder="ТНВЭД, столбец №"
//                             // required
//                         />
//
//                         {/*<Checkbox*/}
//                         {/*    checked={hasAirConditioning}*/}
//                         {/*    onChange={(event) => setHasAirConditioning(event.currentTarget.checked)}*/}
//                         {/*    label="Наличие кондиционера"*/}
//                         {/*/>*/}
//                     </div>
//                     <div>
//                         <h3>Настройки своего прайса</h3>
//                         <NumberInput
//                             value={ownPriceColNumber}
//                             onChange={setOwnPriceColNumber}
//                             placeholder="Наша цена, столбец №"
//                             // required
//                         />
//                         <NumberInput
//                             value={ownOnStockColNumber}
//                             onChange={setOwnOnStockColNumber}
//                             placeholder="Наш остаток, столбец №"
//                             // required
//                         />
//                         <NumberInput
//                             value={ownReservedOnStockColNumber}
//                             onChange={setOwnReservedOnStockColNumber}
//                             placeholder="Наш резерв, столбец №"
//                             // required
//                         />
//                         <NumberInput
//                             value={ownFreeOnStockColNumber}
//                             onChange={setOwnFreeOnStockColNumber}
//                             placeholder="У нас свободно, столбец №"
//                             // required
//                         />
//                         <NumberInput
//                             value={ownPriceForSiteColNumber}
//                             onChange={setOwnPriceForSiteColNumber}
//                             placeholder="Цена для сайта, столбец №"
//                             // required
//                         />
//
//                     </div>
//                 </div>
//                 <div style={{display: 'flex', justifyContent: 'flex-start', gap: '1em', marginTop: '1em'}}>
//                     <Button onClick={saveData}>Отправить</Button>
//                     <Button onClick={handleReset}>Обнулить значения</Button>
//                     <Button onClick={handleDateSubmit}>Запросить курс НБ РБ</Button>
//                 </div>
//             </form>
//             <div>{dateResponse}</div>
//         </div>
//     );
// }
//
// export default AddPrice;
