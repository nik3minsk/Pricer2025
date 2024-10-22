import React, {useState} from "react";
import {Button, TextInput} from "@mantine/core";

const BACK_URL:string = import.meta.env.VITE_BACK_URL
console.log(BACK_URL)


// const Text1 = (): React.JSX.Element => {
//     const [value, setValue] = useState('');
//     const test = async () => {
//         await fetch(`${BACK_URL}/api/main`)
//             .then((el)=>(el.json()))
//             .catch((e)=>console.log(e))
//         // console.log(test())
//     };

const Text1 = () => {
    const [value, setValue] = useState('');

    const test = async () => {
        try {
            const response = await fetch(`${BACK_URL}/api/main`);
            const data = await response.json();
            console.log(data); // Выводим ответ в консоль
        } catch (e) {
            console.log(e);
        }
    };







    // console.log(Text1)
    // return (fetch(BASE_URL).then((el) => el.json()))
    // return(fetch("https://google.com").then((el)=> el.json()))
    return (
    <>
        <TextInput
            value={value}
            onChange={(event) => setValue(event.currentTarget.value)}
        />
        <Button onClick={() => {
            console.log(test())
            // console.log(value)
        }}>Knopka</Button>

    </>
);
}

export default Text1
