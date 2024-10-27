import React, { useState, useEffect } from "react";
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/dropzone/styles.css';
import { Button, Group, Stack, Table } from "@mantine/core"; // Импортируем компонент Table
import { useDisclosure } from "@mantine/hooks";
import PriceAddAndEdit from './priceAddAndEdit';
import axios from "axios";

const BACK_URL = import.meta.env.VITE_BACK_URL;

const HomePage = (): React.JSX.Element => {
    const [openedGeneralPrice, { open: openGeneralPrice, close: closeGeneralPrice }] = useDisclosure(false);
    const [openedSupplierPrice, { open: openSupplierPrice, close: closeSupplierPrice }] = useDisclosure(false);
    const [serverResponse, setServerResponse] = useState('');
    const [responseType, setResponseType] = useState(''); // To track the type of response
    const [sellers, setSellers] = useState([]); // State to store sellers data

    // Fetch sellers data from API
    useEffect(() => {
        const fetchSellers = async () => {
            try {
                const response = await axios.get(`${BACK_URL}/api/sellers`);
                setSellers(response.data);
                console.log(response);
            } catch (error) {
                console.error('Error fetching sellers:', error);
            }
        };

        fetchSellers();
    }, []);

    // Добавим useEffect для сброса сообщения через 2 секунды
    useEffect(() => {
        if (serverResponse) {
            const timer = setTimeout(() => {
                setServerResponse('');
            }, 2000);
            return () => clearTimeout(timer);
        }
    }, [serverResponse]);

    return (
        <Stack align="center" justify="center" style={{ minHeight: '100vh' }}>
            <Group align="start" style={{ width: '100%' }}>
                <div style={{ flex: 1 }}>
                    <h2>Прайс-листы</h2>
                    {/* Здесь добавьте код для отображения прайс-листов */}
                    <Table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Название прайса</th>
                            <th>Путь к прайсу</th>
                            <th>Является основным</th>
                        </tr>
                        </thead>
                        <tbody>
                        {sellers.map(seller => (
                            <tr key={seller.id}>
                                <td>{seller.id}</td>
                                <td>{seller.priceName}</td>
                                <td>{seller.pathToPrice}</td>
                                <td>{seller.isGeneralPrice ? 'Да' : 'Нет'}</td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                </div>
                <div style={{ flexShrink: 0 }}>
                    <Button onClick={openGeneralPrice} fullWidth style={{ marginBottom: '10px' }}>Добавить собственный прайс</Button>
                    <Button onClick={openSupplierPrice} fullWidth>Добавить прайс поставщика</Button>
                    {serverResponse && (
                        <pre style={{ marginTop: '20px', color: 'green' }}>{serverResponse}</pre>
                    )}
                </div>
            </Group>
            <PriceAddAndEdit
                isGeneralPrice={true}
                priceNumber={0}
                opened={openedGeneralPrice}
                close={closeGeneralPrice}
                setServerResponse={setServerResponse}
                setResponseType={setResponseType} // Передаем setResponseType
            />
            <PriceAddAndEdit
                isGeneralPrice={false}
                priceNumber={0}
                opened={openedSupplierPrice}
                close={closeSupplierPrice}
                setServerResponse={setServerResponse}
                setResponseType={setResponseType} // Передаем setResponseType
            />
        </Stack>
    );
}

export default HomePage;
