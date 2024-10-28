import '@mantine/core/styles.css';
// import './App.css';
import {AppShell, MantineProvider} from '@mantine/core';

// import Header from './Components/Header.tsx';
import AuthorisationPage from "./Components/AuthorisationPage.tsx";
import {Route} from "wouter";
import Text1 from "./Components/Text1.tsx";
import HomePage from "./Components/HomePage.tsx";
// import AddSeller from "./Components/AddSeller.tsx";

function App() {
    return (
        <MantineProvider>
            <AppShell padding="md">
                {/*<Header/>*/}
                <AppShell.Main>
                    <Route path="/home">
                        {params => <HomePage {...params}/>}
                    </Route>
                    {/*<Route path="/header">*/}
                    {/*    {params => <Header {...params}/>}*/}
                    {/*</Route>*/}
                    <Route path="/addprice">
                        {params => <AddPrice {...params}/>}
                    </Route>
                    <Route path="/addseller">
                        {/*{params => <AddSeller {...params}/>}*/}
                        {params => <AddSeller {...params}/>}
                    </Route>
                    <Route path="/AuthorisationPage">
                        {params => <AuthorisationPage {...params}/>}
                    </Route>
                    <Route path="/text1">
                        {params => <Text1 {...params}/>}
                    </Route>
                </AppShell.Main>
            </AppShell>
        </MantineProvider>
    );
}

export default App;
