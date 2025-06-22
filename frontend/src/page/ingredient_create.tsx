import { useNavigate, useParams } from "react-router-dom";
import {type ChangeEvent, type FormEvent, useCallback, useEffect, useRef, useState} from "react";
import axios from "axios";
import type {
    IngredientCreate,
    IngredientProductRef,
    IngredientProfile,
    Nutrient,
    Nutrients
} from "../types.ts";
import IngredientLayout from "../layout/ingredient_layout.tsx";
import NutrientLayout from "../layout/nutrient_layout.tsx";
import { handleAxiosFormError,
    mapNutrientsToCreateNutrientArray
} from "../helper.ts";
import ShowError from "../component/ShowError.tsx";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";

export default function IngredientCreate(){
    const { addToRecipe } = useRecipeCart()
    const {product} = useParams<string>()
    const routeTo = useNavigate();

    const refProduct = useRef<IngredientProductRef>({
        focusField: () => {
        }
    });

    const [ingredientCreate, setIngredientCreate] = useState<IngredientCreate>()
    const [nutrientArray, setNutrientArray] = useState<Nutrient[]>( [] )

    const [isLoading, setLoading] = useState<boolean>( false )

    const [isError, setError] = useState<string>( "" )

    const getNutrients = useCallback( async () => {
        try {
                setLoading( true )
                const responseNutrient = await axios.get<Nutrients[]>( "/eyf/ingredients/daily/nutrients" );
            setNutrientArray( mapNutrientsToCreateNutrientArray( responseNutrient ) );
        } catch (error) {
            if ( axios.isAxiosError( error ) ) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        } finally {
            setLoading( false )
        }
    }, [] )

    function handleChangeNutrient( e:ChangeEvent<HTMLInputElement> ) {
        e.preventDefault();
        const { name, value } = e.target
        const updateNutrients:Nutrient[] = nutrientArray.map( ( n) =>
            ( n.name === name ) && ( n.unit !== "kJ" )
                ? { ...n, quantity: Number( value ) } as Nutrient
                : n
        );
        setNutrientArray( updateNutrients )
        setError( "" );
    }
    function handleChangeIngredientCreate( e:ChangeEvent<HTMLInputElement> ) {
        e.preventDefault();
        const { name, value } = e.target;
        const newValue:string|number = name === "price" ? Number (value) : String (value);
        setIngredientCreate(prevState => ( { ...prevState, [name]: newValue } as IngredientCreate ) );
        setError( "" );
    }

    const submit = async ( e:FormEvent<HTMLFormElement> )=> {
        e.preventDefault();
        let messages = { userMessage:"", logMessage:"" };
        if( ingredientCreate ) {
            setError( "" );
            const ingredientProfile:IngredientProfile = {
                ingredientCreate:ingredientCreate,
                nutrientsArray:nutrientArray
            }
            try {
                const response = await axios.post("/eyf/ingredients", ingredientProfile,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    } );
                addToRecipe(response.data)
            } catch ( error ) {
                messages = handleAxiosFormError(
                    error,
                    refProduct,
                    "product"
                );
                setError( messages.userMessage );
                console.error( messages.logMessage );
            }
            if( messages.userMessage === "" ) {
                routeTo( "/recipeplan" );
            }
        }
    }

    useEffect( () => {
        if( product ) {
            const fetchIngredientCreate = async () => {
                const newIngredientCreate: IngredientCreate = {
                    product: product,
                    variation: "",
                    quantity: 100,
                    unit: "g",
                    prices: 0
                }
                return newIngredientCreate
            }
            fetchIngredientCreate()
                .then( ( response:IngredientCreate ) => setIngredientCreate( response ) )
                .catch( ( error) => console.error( 'Error while fetching ingredientCreate:', error ) )
        }
    }, [product] );
    useEffect( () => {
        ( async () => {
            await getNutrients();
        } ) ();
    }, [getNutrients] );

    return(
        <>
            <div>
                { isLoading && (
                    <h1> Einen Moment bitte Zutat wird geladen ...</h1>
                ) }
                {ingredientCreate && (
                    <h1> Hier kannst Du { ingredientCreate.product } erstellen:  </h1>
                ) }
                { isError !== "" && (
                    <ShowError message = { isError } />
                ) }
                <form  onSubmit={ ( e)=>submit( e ) } >
                    { ingredientCreate && (
                        <IngredientLayout
                            ingredient = { ingredientCreate }
                            onChange = { handleChangeIngredientCreate }
                            ref = { refProduct }
                        />
                    ) }
                    { nutrientArray && (
                        <NutrientLayout
                            nutrients={ nutrientArray }
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
    )

}