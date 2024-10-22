import '@mantine/core/styles.css';
import { TextInput, NumberInput, Button, Checkbox } from '@mantine/core';
import { useState } from 'react';
import axios from 'axios';

const BACK_URL = import.meta.env.VITE_BACK_URL;

function AddPrice() {
    console.log('Component rendered');

    const [value, setValue] = useState('');
    const [value1, setValue1] = useState<string | number>('');
    const [surname, setSurname] = useState('');
    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const [price, setPrice] = useState<number | string>('');
    const [discount, setDiscount] = useState<number | string>('');
    const [monthlyPayment, setMonthlyPayment] = useState<number | string>('');
    const [color, setColor] = useState('');
    const [upholstery, setUpholstery] = useState('');
    const [hasAirConditioning, setHasAirConditioning] = useState(false);
    const [dateResponse, setDateResponse] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        const response = await axios.post(`${BACK_URL}/api/currency`, {
            value,
            value1,
            owner: {
                surname,
                name,
                phone
            },
            financialConditions: {
                price,
                discount,
                monthlyPayment
            },
            carDetails: {
                color,
                upholstery,
                hasAirConditioning
            }
        }, {
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const data = await response.data;
        console.log(data);
    };

    const handleDateSubmit = async () => {
        const currentDate = new Date().toISOString();

        const response = await axios.post(`${BACK_URL}/api/nbcurrency`, { date: currentDate }, {
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const data = await response.data;
        setDateResponse(data);
    };

    const handleReset = () => {
        setValue('');
        setValue1('');
        setSurname('');
        setName('');
        setPhone('');
        setPrice('');
        setDiscount('');
        setMonthlyPayment('');
        setColor('');
        setUpholstery('');
        setHasAirConditioning(false);
    };

    return (
        <div>
            <b>Заполните информацию о новом прайсе</b>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start', gap: '1em' }}>
                <div style={{ display: 'flex', flexDirection: 'row', gap: '2em' }}>
                    <div>
                        <h3>Общие сведения</h3>
                        <TextInput
                            value={surname}
                            onChange={(event) => setSurname(event.currentTarget.value)}
                            placeholder="Название прайса"
                            required
                        />
                        <TextInput
                            value={name}
                            onChange={(event) => setName(event.currentTarget.value)}
                            placeholder="Путь к прайсу"
                            required
                        />
                        {/*<TextInput*/}
                        {/*    value={phone}*/}
                        {/*    onChange={(event) => setPhone(event.currentTarget.value)}*/}
                        {/*    placeholder="Введите телефон"*/}
                        {/*    required*/}
                        {/*/>*/}
                    </div>
                    <div>
                        <h3>Финансовые правила</h3>
                        <NumberInput
                            value={price}
                            onChange={setPrice}
                            placeholder="Учтенный % НДС "
                            required
                        />
                        <NumberInput
                            value={discount}
                            onChange={setDiscount}
                            placeholder="Код валюты"
                            required
                        />
                        <NumberInput
                            value={monthlyPayment}
                            onChange={setMonthlyPayment}
                            placeholder="Коэфф. на конвертацию"
                            required
                        />
                        {/*<NumberInput*/}
                        {/*    value={monthlyPayment}*/}
                        {/*    onChange={setMonthlyPayment}*/}
                        {/*    placeholder="Коэфф. на конвертацию"*/}
                        {/*    required*/}
                        {/*/>*/}


                    </div>
                    <div>
                        <h3>Номера столбцов для парсинга</h3>
                        <TextInput
                            value={color}
                            onChange={(event) => setColor(event.currentTarget.value)}
                            placeholder="Введите цвет"
                            required
                        />
                        <TextInput
                            value={upholstery}
                            onChange={(event) => setUpholstery(event.currentTarget.value)}
                            placeholder="Введите тип обивки"
                            required
                        />
                        <Checkbox
                            checked={hasAirConditioning}
                            onChange={(event) => setHasAirConditioning(event.currentTarget.checked)}
                            label="Наличие кондиционера"
                        />
                    </div>
                </div>
                <div style={{ display: 'flex', justifyContent: 'flex-start', gap: '1em', marginTop: '1em' }}>
                    <Button type="submit">Отправить</Button>
                    <Button onClick={handleReset}>Обнулить значения</Button>
                    <Button onClick={handleDateSubmit}>Запросить курс НБ РБ</Button>
                </div>
            </form>
            <div>{dateResponse}</div>
        </div>
    );
}

export default AddPrice;
