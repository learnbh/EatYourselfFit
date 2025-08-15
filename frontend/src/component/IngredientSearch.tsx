import type {Ingredient} from "../types.ts";
import {type ChangeEvent, useCallback, useEffect} from "react";
import axios from "axios";

type Props = {
    placeholder: string
    name:string
    searchWord:string
    setSearchWord: (search:string) => void
    setSearchResult: (result: Ingredient[]) => void
    setSearchNotFoundVisible: (searchNotFoundVisible: boolean) => void
    setIsLoading: (isLoading: boolean) => void
}
export default function IngredientSearch(props:Readonly<Props>){

    const { searchWord, setSearchWord, setSearchResult, setIsLoading, setSearchNotFoundVisible } = props;
    
    function handleChangeIngredient(e:ChangeEvent<HTMLInputElement>){
        e.preventDefault();
        setSearchWord( e.target.value);
    }

    const getDBData = useCallback(async (text:string) => {
        if(text.trim() === ""){
            setSearchResult([]);
            setIsLoading(false);
            setSearchNotFoundVisible(false);
            return;
        }
        try {
            setIsLoading(true);
            const response = await axios.get("/eyf/ingredients/name/" + text);
            if (response.data.length > 0) {
                setSearchResult(response.data);
                setSearchNotFoundVisible(false);
            } else {
                setSearchResult([]);
                setSearchNotFoundVisible(true);
            }
        } catch (error){
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        }finally {
            setIsLoading(false);
        }
    }, [setIsLoading, setSearchNotFoundVisible, setSearchResult]);

    useEffect(() => {
        (async () => {
            await getDBData(searchWord);
        })();
    }, [getDBData, searchWord]);

    return(
        <>
            <div className="flex flex-col">
                <input
                    placeholder={props.placeholder}
                    value={searchWord}
                    name={props.name}
                    id={props.name}
                    onChange={handleChangeIngredient}
                />
            </div>
        </>
    )
}