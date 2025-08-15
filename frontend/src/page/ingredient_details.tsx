import {type ChangeEvent, type FormEvent, useCallback, useEffect, useRef, useState} from "react";
import axios, {type AxiosResponse} from "axios";
import type {Ingredient, IngredientProductRef, Nutrient, Nutrients} from "../types.ts";
import IngredientLayout from "../layout/ingredient_layout.tsx";
import NutrientLayout from "../layout/nutrient_layout.tsx";
import { handleAxiosFormError, ingredientToDto, mapNutrientsToNutrientArray } from "../helper.ts";
import ShowError from "../component/ShowError.tsx";
import {useNavigate} from "react-router-dom";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";

export default function IngredientDetails() {
    const { addToRecipe } = useRecipeCart()

    const routeTo = useNavigate();
    const refProduct = useRef<IngredientProductRef>({
        focusField: () => {}
    });

    const ingredientId: string | null = sessionStorage.getItem("detailId");
    const [ingredient, setIngredient] = useState<Ingredient>()
    const [nutrients, setNutrients] = useState<Nutrient[]>([])

    const [isLoading, setLoading] = useState<boolean>(false)

    const [isIngredientChanged, setIngredientChanged] = useState<boolean>(false)
    const [isNutrientChanged, setNutrientChanged] = useState<boolean>(false)
    const [isError, setError] = useState<string>("")

    const getDetails = useCallback( async ( id: string | null ) => {
        try {
            if (id) {
                setLoading(true)
                const responseIngredient = await axios.get("/eyf/ingredients/ingredient/detail/" + id)
                const ingredient: Ingredient = responseIngredient.data
                setIngredient(ingredient)
                if (ingredient) {
                    const responseNutrient = await axios.get<Nutrients[]>("/eyf/nutrients/detail/" + ingredient.nutrientsId);
                    setNutrients(mapNutrientsToNutrientArray(responseNutrient));
                }
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        } finally {
            setLoading(false)
        }
    }, [])

    function handleChangeNutrient(e:ChangeEvent<HTMLInputElement>) {
        e.preventDefault();
        const {name, value} = e.target
        const updateNutrients:Nutrient[] = nutrients.map((n) =>
            (n.name === name) && (n.unit !== "kJ")
                ? { ...n, quantity: Number(value) } as Nutrient
                : n
        );
        setNutrients(updateNutrients)
        setNutrientChanged(true);
        setError( "" );
    }
    function handleChangeIngredient(e:ChangeEvent<HTMLInputElement>) {
        e.preventDefault();
        const { name, value } = e.target;
        const newValue:string|number = name === "price" ? Number (value) : String (value);
        setIngredient(prevState => ( { ...prevState, [name]: newValue } as Ingredient ) );
        setIngredientChanged(true);
        setError( "" );
    }

    const submit = async (e:FormEvent<HTMLFormElement>)=> {
        e.preventDefault();
        let messages = { userMessage:"", logMessage:"" };
        if(ingredient) {
            if ( isIngredientChanged || isNutrientChanged ) {
                setError( "" );
                try {
                    const response = await axios.put("/eyf/ingredients/"+ingredientId, ingredientToDto(ingredient));
                    setIngredient(response.data);
                    addToRecipe(response.data)
                    setIngredientChanged(false);
                } catch ( error ) {
                    messages = handleAxiosFormError(
                        error,
                        refProduct,
                        "product"
                    );
                    setError( messages.userMessage );
                    console.error( messages.logMessage );
                }
                if ( isNutrientChanged ) {
                    try {
                        const response = await axios.put<AxiosResponse>("/eyf/nutrients/" + ingredient.nutrientsId, nutrients)
                        setNutrients(mapNutrientsToNutrientArray(response));
                        setNutrientChanged(false)
                    } catch (error){
                        if (axios.isAxiosError(error)) {
                            console.error('Axios error:', error.response?.data || error.message);
                        } else {
                            console.error('Unexpected error:', error);
                        }
                    }
                }
                await getDetails(ingredientId);
                if( messages.userMessage === "" ) {
                    routeTo( "/recipeplan" );
                }
            } else {
                setError( "Es wurde nichts geändert. Speichern abgebrochen." )
            }
        }
    }

    useEffect( () => {
        (async () => {
            await getDetails(ingredientId);
        })();
    }, [ingredientId, getDetails] );

    return (
        <>
            { isLoading && (
                <h1> Einen Moment bitte Zutat wird geladen ...</h1>
            ) }
            <div>
                <h1 className="p-5"> Hier findest Du Informationen zu einer Zutat und <br/>Du kannst sie auch bearbeiten. </h1>
                { isError !== "" && (
                    <ShowError message = { isError } />
                ) }
                <form  onSubmit={(e)=>submit(e)}>
                    { ingredient && (
                        <IngredientLayout
                            ingredient={ ingredient }
                            onChange={ handleChangeIngredient }
                            ref = { refProduct }
                        />
                    )}
                    { nutrients && (
                        <NutrientLayout
                            nutrients={ nutrients }
                            onChange={ handleChangeNutrient }
                        />
                    ) }
                    { isError !== "" && (
                        <ShowError message = { isError } />
                    ) }
                    <button className="border pt-2 pb-2 w-full" type="submit" >
                        Speichern
                    </button>
                </form>
            </div>
        </>
    );
}