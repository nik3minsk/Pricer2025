import React, {useState, useEffect} from "react";
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/dropzone/styles.css';
import {Button, Group, Stack, Checkbox, Modal, Box, Title, TextInput, NumberInput} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import {PriceSettings} from './SellerAddAndEdit.tsx';
import axios from "axios";
import FileSelector from "./FileSelector.tsx";
import CurrencySelector from "./CurrencySelector.tsx";
// import {eventCenter} from "recharts/types/util/Events";

const BACK_URL = import.meta.env.VITE_BACK_URL;





        const HomePage = (): React.JSX.Element => {
            const [openedPrice, {open: openPrice, close: closePrice}] = useDisclosure(false);
            const [openedHelp, {open: openHelp, close: closeHelp}] = useDisclosure(false);
            const [serverResponse, setServerResponse] = useState('');
            const [responseType, setResponseType] = useState('');
            const [sellers, setSellers] = useState<SellerDTO[]>([]);
            const [selectedSellerId, setSelectedSellerId] = useState<number | undefined>(undefined);
            const [hasGeneralPrice, setHasGeneralPrice] = useState(false);
            const [ownSettings, setOwnSettings] = useState<PriceSettings>();

            console.log(selectedSellerId)
            const [isGeneralPrice, setIsGeneralPrice] = useState(true);

            const [priceName, setPriceName] = useState('');
            const [pathToPrice, setPathToPrice] = useState('');
            const [currencyCode, setCurrencyCode] = useState<number | string>('');
            const [vatRate, setVatRate] = useState<number | string>('');
            const [currencyBankCoeff, setCurrencyBankCoeff] = useState<number | string>('');
            const [coeffDeliveryCost, setCoeffDeliveryCost] = useState<number | string>('');
            const [minOrderSum, setMinOrderSum] = useState<number | string>('');
            const [brandColNumber, setBrandColNumber] = useState<number | string>('');
            const [articleColNumber, setArticleColNumber] = useState<number | string>('');
            const [productCategoryColNumber, setProductCategoryColNumber] = useState<number | string>('');
            const [productNameColNumber, setProductNameColNumber] = useState<number | string>('');
            const [priceColNumber, setPriceColNumber] = useState<number | string>('');
            const [onStockColNumber, setOnStockColNumber] = useState<number | string>('');
            const [barcodeColNumber, setBarcodeColNumber] = useState<number | string>('');
            const [tnvedColNumber, setTnvedColNumber] = useState<number | string>('');
            const [ownPriceColNumber, setOwnPriceColNumber] = useState<number | string>('');
            const [ownOnStockColNumber, setOwnOnStockColNumber] = useState<number | string>('');
            const [ownReservedOnStockColNumber, setOwnReservedOnStockColNumber] = useState<number | string>('');
            const [ownFreeOnStockColNumber, setOwnFreeOnStockColNumber] = useState<number | string>('');
            const [ownPriceForSiteColNumber, setOwnPriceForSiteColNumber] = useState<number | string>('');

            const [headerRowNumber, setHeaderRowNumber] = useState<number | string>('');
            const [startPriceRowNumber, setStartPriceRowNumber] = useState<number | string>('');

            const handleClearValues = () => {
                setPriceName('');
                setPathToPrice('');
                setCurrencyCode('');
                setVatRate('');
                setCurrencyBankCoeff('');
                setCoeffDeliveryCost('');
                setMinOrderSum('');


                setStartPriceRowNumber('');
                setHeaderRowNumber('');

                setBrandColNumber('');
                setArticleColNumber('');
                setProductCategoryColNumber('');
                setProductNameColNumber('');
                setPriceColNumber('');
                setOnStockColNumber('');
                setBarcodeColNumber('');
                setTnvedColNumber('');
                setOwnPriceColNumber('');
                setOwnOnStockColNumber('');
                setOwnReservedOnStockColNumber('');
                setOwnFreeOnStockColNumber('');
                setOwnPriceForSiteColNumber('');
                setServerResponse('');
            };

            const handleSaveData = async () => {
                if (!priceName || !pathToPrice || !currencyCode || typeof vatRate === 'undefined' || !brandColNumber || !articleColNumber || !productNameColNumber || !priceColNumber || !startPriceRowNumber) {
                    alert('Заполните все обязательные поля');
                    return;
                }

                const data = {
                    // isGeneralPrice,
                    priceNumber: 0,
                    sellerDetails: {
                        isGeneralPrice,
                        priceName,
                        pathToPrice,
                        currencyCode,
                        vatRate,
                        currencyBankCoeff,
                        coeffDeliveryCost,
                        minOrderSum
                    },
                    rules_for_xlsx_columns: {

                        brandColNumber,
                        articleColNumber,
                        productCategoryColNumber,
                        productNameColNumber,
                        priceColNumber,
                        onStockColNumber,
                        barcodeColNumber,
                        tnvedColNumber,
                        ownPriceColNumber,
                        ownOnStockColNumber,
                        ownReservedOnStockColNumber,
                        ownFreeOnStockColNumber,
                        ownPriceForSiteColNumber,
                        startPriceRowNumber,
                        headerRowNumber,
                    }
                };
                console.log(data)
                try {
                    const response = await axios.post(`${BACK_URL}/api/addSeller`, data, {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    });
                    const responseData = await response.data;
                    // setServerResponse(responseData);
                    setServerResponse('Прайс добавлен'); // Устанавливаем сообщение
                    setResponseType('success');
                    close(); // Закрыть модальное окно после успешного сохранения
                } catch (error) {
                    setServerResponse('Ошибка при сохранении данных');
                    setResponseType('error');
                }
            };

            const folderPath = "/Users/nik3minsk/IdeaProjects/pricer2025/xlsx"; // Set your folder path here
            const handleFileSelect = (file:any) => {
                setPathToPrice(file);
                // setValue(file); // Set the selected file path to your state

            };

            interface SellerDTO {
                id: number;
                priceName: string;
                pathToPrice: string;
                isGeneralPrice: boolean;
            }

            const fetchSellers = async () => {
                try {
                    const response = await axios.get(`${BACK_URL}/api/sellers`);
                    setSellers(response.data);
                    setHasGeneralPrice(response.data.some((seller: SellerDTO) => seller.isGeneralPrice));
                    console.log(response);
                } catch (error) {
                    console.error('Ошибка при получении данных о продавцах:', error);
                }
            };

            useEffect(() => {
                fetchSellers();
            }, []);

            useEffect(() => {
                if (serverResponse) {
                    const timer = setTimeout(() => {
                        setServerResponse('');
                        setResponseType('');
                        fetchSellers();
                    }, 1400);
                    return () => clearTimeout(timer);
                }
            }, [serverResponse]);

            const handleCheckboxChange = (id: number) => {
                setSelectedSellerId(selectedSellerId === id ? undefined : id);
            };

            const handleDelete = async () => {
                if (selectedSellerId) {
                    try {
                        await axios.delete(`${BACK_URL}/api/sellers/${selectedSellerId}`);
                        setSellers(sellers.filter(seller => seller.id !== selectedSellerId));
                        setSelectedSellerId(undefined);
                        setServerResponse('Прайс успешно удален');
                    } catch (error) {
                        console.error('Ошибка при удалении прайса:', error);
                        setServerResponse('Ошибка при удалении прайса');
                    }
                } else {
                    setServerResponse('Выберите прайс для удаления');
                }
            };

            // Функция для преобразования исходного объекта в объект типа PriceSettings
            function transformToPriceSettings(source: any): PriceSettings {
                return {
                    priceName: source.priceName,
                    pathToPrice: source.pathToPrice,
                    currencyCode: source.economicRules.currencyCode.currencyCode,
                    vatRate: source.economicRules.vatPercentInPrice,
                    currencyBankCoeff: source.economicRules.currencyCoefficient,
                    coeffDeliveryCost: source.economicRules.deliveryCoefficient,
                    minOrderSum: source.economicRules.minOrderSum,
                    brandColNumber: source.xlsPriceRules.columnBrand,
                    articleColNumber: source.xlsPriceRules.columnArticle,
                    productCategoryColNumber: source.xlsPriceRules.columnProductCategory,
                    productNameColNumber: source.xlsPriceRules.columnProductName,
                    priceColNumber: source.xlsPriceRules.columnPrice,
                    onStockColNumber: source.xlsPriceRules.columnOnStock,
                    barcodeColNumber: source.xlsPriceRules.columnBarcode,
                    tnvedColNumber: source.xlsPriceRules.columnTnved,
                    ownPriceColNumber: source.xlsPriceRules.columnPriceOnStockOwn,
                    ownOnStockColNumber: source.xlsPriceRules.columnOnStockOwn,
                    ownReservedOnStockColNumber: source.xlsPriceRules.columnReservedOnStockOwn,
                    ownFreeOnStockColNumber: source.xlsPriceRules.columnFreeOnStock,
                    ownPriceForSiteColNumber: source.xlsPriceRules.columnPriceForSiteOwn,
                    headerRowNumber: source.xlsPriceRules.headerStringNumber,
                    startPriceRowNumber: source.xlsPriceRules.startPriceDataRowNumber
                };
            }

            const editPrice = async () => {
                if (selectedSellerId) {
                    try {
                        const response = await axios.get(`${BACK_URL}/api/sellers/${selectedSellerId}`);
                        const editVals = transformToPriceSettings(response.data)
                        setIsGeneralPrice(response.data.isGeneralPrice)
                        setPriceName(editVals.priceName);
                        setPathToPrice(editVals.pathToPrice);
                        setCurrencyCode(editVals.currencyCode);
                        setVatRate(editVals.vatRate);
                        setCurrencyBankCoeff(editVals.currencyBankCoeff);
                        setCoeffDeliveryCost(editVals.coeffDeliveryCost);
                        setMinOrderSum(editVals.minOrderSum);


                        setStartPriceRowNumber(editVals.startPriceRowNumber);
                        setHeaderRowNumber(editVals.headerRowNumber);

                        setBrandColNumber(editVals.brandColNumber);
                        setArticleColNumber(editVals.articleColNumber);
                        setProductCategoryColNumber(editVals.productCategoryColNumber);
                        setProductNameColNumber(editVals.productNameColNumber);
                        setPriceColNumber(editVals.priceColNumber);
                        setOnStockColNumber(editVals.onStockColNumber);
                        setBarcodeColNumber(editVals.barcodeColNumber);
                        setTnvedColNumber(editVals.tnvedColNumber);
                        setOwnPriceColNumber(editVals.ownPriceColNumber);
                        setOwnOnStockColNumber(editVals.ownOnStockColNumber);
                        setOwnReservedOnStockColNumber(editVals.ownReservedOnStockColNumber);
                        setOwnFreeOnStockColNumber(editVals.ownFreeOnStockColNumber);
                        setOwnPriceForSiteColNumber(editVals.ownPriceForSiteColNumber);

                        openPrice()

                    } catch (error) {
                        // console.error('Ошибка при удалении прайса:', error);
                        // setServerResponse('Ошибка при удалении прайса');
                    }
                } else {
                    // setServerResponse('Выберите прайс для удаления');
                }
            };


            // const editPrice = () => {
            //     const [openedHelp, setOpenedHelp] = useState(false);
            //     const [modalData, setModalData] = useState("");
            //     const openHelp = () => setOpenedHelp(true);
            //     const closeHelp = () => setOpenedHelp(false);
            //     const editPrice = async () => {
            //         if (selectedSellerId) {
            //             try {
            //                 const response = await axios.get(`${BACK_URL}/api/sellers/${selectedSellerId}`);
            //                 const ownSettings = transformToPriceSettings(response.data);
            //                 setModalData(`Цена: ${ownSettings.priceName}\nПуть: ${ownSettings.pathToPrice}\nКод валюты: ${ownSettings.currencyCode}\n...`);
            //                 setOwnSettings(ownSettings);
            //                 openHelp() // Открываем модальное окно после получения данных }
            //             catch
            //                 (error)
            //                 {
            //                     console.error("Ошибка при получении данных продавца", error);
            //                 }
            //             }
            //         };














            // const handleComparePrices = async () => {
            //     const sellerData = sellers.map(seller => ({
            //         id: seller.id,
            //         priceName: seller.priceName,
            //         isGeneralPrice: seller.isGeneralPrice
            //     }));
            //
            //     try {
            //         const response = await axios.post(`${BACK_URL}/api/files/compare`, sellerData);
            //         setServerResponse(`Файл сравнения создан: ${response.data}`);
            //     } catch (error) {
            //         console.error('Ошибка при сравнении цен:', error);
            //         setServerResponse('Ошибка при сравнении цен');
            //     }
            // };


            const handleComparePrices = async () => {
                const sellerData = sellers.map(seller => ({
                    id: seller.id,
                    priceName: seller.priceName,
                    isGeneralPrice: seller.isGeneralPrice
                }));
                try {
                    const response = await axios.post(`${BACK_URL}/api/files/compare`, sellerData);
                    console.log(response)
                    const filePath = response.data; // Загружаем файл пользователю через браузер
                    console.log(filePath)

                    setServerResponse('Сформирован файл - ' + response.data)
                    // const fileResponse = await axios.get(filePath, {responseType: 'blob',});
                    // const url = window.URL.createObjectURL(new Blob([fileResponse.data]));
                    // const link = document.createElement('a');
                    // link.href = url;

                    // link.setAttribute('download', 'comparison_file.xlsx'); // Или  response.headers['content-disposition'] для имени файла
                    // link.setAttribute('download', response.data); // Или  response.headers['content-disposition'] для имени файла

                    // document.body.appendChild(link);
                    // link.click();
                } catch (error) {
                    console.error('Ошибка при сравнении цен:', error);
                    setServerResponse('Ошибка при сравнении цен???');
                }
            };


            return (
                <Stack align="center" justify="center" style={{minHeight: '100vh'}}>
                    <Group align="start"
                           style={{
                               width: '100%',
                               border: '2px solid #ccc',
                               padding: '20px',
                               borderRadius: '10px',
                               position: 'relative'
                           }}>
                        <div style={{flex: 1}}>
                            <h2>Список поставщиков</h2>

                            <div style={{
                                maxHeight: '600px',
                                overflowY: 'auto',
                                paddingRight: '10px',
                                minHeight: '300px',
                                marginTop: '30px'
                            }}>
                                {sellers.slice(0, 10).map(seller => (
                                    <Checkbox
                                        key={seller.id}
                                        label={
                                            seller.isGeneralPrice
                                                ? `Наш прайс: ${seller.priceName} (${seller.pathToPrice})`
                                                : `Поставщик: ${seller.priceName} (${seller.pathToPrice})`
                                        }
                                        checked={selectedSellerId === seller.id}
                                        onChange={() => handleCheckboxChange(seller.id)}
                                        style={{
                                            marginBottom: '10px',
                                            color: seller.isGeneralPrice ? 'green' : 'inherit'
                                        }}
                                    />
                                ))}
                            </div>


                        </div>
                        <div style={{
                            flex: 1,
                            justifyContent: 'space-between',
                            height: '100%',
                            width: '300px',
                            overflow: 'hidden'
                        }}>
                            <Button onClick={()=>{
                                setIsGeneralPrice(true)
                                openPrice()
                            }} fullWidth
                                    style={{
                                        marginBottom: '10px',
                                        cursor: hasGeneralPrice ? 'not-allowed' : 'pointer'
                                    }}
                                    disabled={hasGeneralPrice}>Добавить собственный прайс</Button>
                            <Button onClick={()=>{
                                setIsGeneralPrice(false)
                                openPrice()
                            }} fullWidth style={{
                                marginBottom: '10px',
                                cursor: sellers.length >= 4 ? 'not-allowed' : 'pointer'
                            }} disabled={sellers.length >= 4}>Добавить прайс поставщика</Button>
                            <Button onClick={editPrice} fullWidth style={{marginBottom: '10px'}}>Редактировать
                                прайс</Button>
                            <Button onClick={handleDelete} fullWidth style={{marginBottom: '30px'}}>Удалить
                                прайс</Button>
                            <Button onClick={handleComparePrices} fullWidth
                                    style={{marginBottom: '10px', backgroundColor: '#00FF00', color: '#000'}}>Сравнить
                                цены</Button>
                            <Button onClick={openHelp} fullWidth style={{
                                marginBottom: '7px',
                                backgroundColor: '#FF9900',
                                color: '#000',
                                width: '100%',
                            }}>Help</Button>


                            <Box style={{marginTop: '20px', width: '100%', overflow: 'hidden'}}>
                                {serverResponse && (
                                    <pre style={{
                                        color: 'green',
                                        whiteSpace: 'pre-wrap',
                                        wordWrap: 'break-word'
                                    }}>{serverResponse}</pre>
                                )}
                            </Box>
                        </div>
                    </Group>
                    <Modal size={'auto'} opened={openedPrice} onClose={closePrice} title="Authentication" centered className="modalCentered">
                        <Stack>
                            <Group grow>
                                <Title ta={"center"} w={"33%"} order={4}>Сведения о прайсе продавца</Title>
                                <Title ta={"center"} w={"33%"} order={4}>Номера столбцов для парсинга</Title>
                                { isGeneralPrice && <Title ta={"center"} w={"33%"} order={4}>Настройки своего прайса</Title> }
                            </Group>
                            <Group align={'start'} grow>
                                <Stack>
                                    <TextInput value={priceName} onChange={(event) => setPriceName(event.currentTarget.value)} placeholder="* Название прайса" required />

                                    {/*<TextInput value={pathToPrice} onChange={(event) => setPathToPrice(event.currentTarget.value)} placeholder="* Ссылка на прайс" required />*/}

                                    {/*файлы выбирает, но не передает значение*/}
                                    {/*<FileSelector folderPath={folderPath} onSelectFile={handleFileSelect} /> /!* Add the FileSelector component *!/*/}
                                    <FileSelector folderPath={folderPath} onSelectFile={handleFileSelect} /> {/* Add the FileSelector component */}

                                    <CurrencySelector onCurrencySelect={setCurrencyCode} />
                                    <NumberInput value={vatRate} onChange={setVatRate} placeholder="* Учтенный % НДС" required />
                                    <NumberInput value={currencyBankCoeff} onChange={setCurrencyBankCoeff} placeholder="Коэфициент банковских потерь" />
                                    <NumberInput value={coeffDeliveryCost} onChange={setCoeffDeliveryCost} placeholder="Коэфициент стоимости доставки" />
                                    {/*<NumberInput value={minOrderSum} onChange={setMinOrderSum} placeholder="Минимальная сумма заказа" />*/}
                                </Stack>
                                <Stack>
                                    <NumberInput value={startPriceRowNumber} onChange={setStartPriceRowNumber} placeholder="* Начало прайса, строка №" required />
                                    <NumberInput value={headerRowNumber} onChange={setHeaderRowNumber} placeholder="* Заголовок, строка №" required />
                                    <NumberInput value={brandColNumber} onChange={setBrandColNumber} placeholder="* Бренд, столбец №" required />
                                    <NumberInput value={articleColNumber} onChange={setArticleColNumber} placeholder="* Артикул, столбец №" required />
                                    <NumberInput value={productCategoryColNumber} onChange={setProductCategoryColNumber} placeholder="Категория, столбец №" />
                                    <NumberInput value={productNameColNumber} onChange={setProductNameColNumber} placeholder="* Название, столбец №" required />
                                    <NumberInput value={priceColNumber} onChange={setPriceColNumber} placeholder="* Цена, столбец №" required />
                                    <NumberInput value={onStockColNumber} onChange={setOnStockColNumber} placeholder="Наличие, столбец №" />
                                    <NumberInput value={barcodeColNumber} onChange={setBarcodeColNumber} placeholder="Баркод, столбец №" />
                                    <NumberInput value={tnvedColNumber} onChange={setTnvedColNumber} placeholder="ТНВЭД, столбец №" />
                                </Stack>
                                {isGeneralPrice && (
                                    <Stack>
                                        <NumberInput value={ownPriceColNumber} onChange={setOwnPriceColNumber} placeholder="Наша цена, столбец №" />
                                        <NumberInput value={ownOnStockColNumber} onChange={setOwnOnStockColNumber} placeholder="Наш остаток, столбец №" />
                                        <NumberInput value={ownReservedOnStockColNumber} onChange={setOwnReservedOnStockColNumber} placeholder="Наш резерв, столбец №" />
                                        <NumberInput value={ownFreeOnStockColNumber} onChange={setOwnFreeOnStockColNumber} placeholder="У нас свободно, столбец №" />
                                        <NumberInput value={ownPriceForSiteColNumber} onChange={setOwnPriceForSiteColNumber} placeholder="Цена для сайта, столбец №" />
                                    </Stack>
                                )}
                            </Group>
                            <Group grow>
                                <Button onClick={()=>{
                                    handleSaveData()
                                    closePrice()
                                    handleClearValues()
                                }}>Сохранить</Button>
                                <Button onClick={handleClearValues}>Очистить значения</Button>
                            </Group>
                        </Stack>
                    </Modal>
                    <Modal opened={openedHelp} onClose={closeHelp} title="Help" centered size="lg"
                           className="modalCentered">
                        <p>Эта программа позволяет управлять списком продавцов, добавлять и удалять прайсы, а
                            также
                            сравнивать
                            цены между различными поставщиками. Используйте кнопки для выполнения
                            соответствующих
                            действий.</p>
                        {/*<p> {modalData.split('\n').map((line, index) => (*/}
                        {/*    <span key={index}>{line}<br/></span>))} </p>*/}
                    </Modal>
                </Stack>
            );
        }

        export default HomePage;
