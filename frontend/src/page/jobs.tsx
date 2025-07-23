import axios from "axios";
import {useState} from "react";
import Job from "../component/Job.tsx";

export default function Jobs() {
    const [migrateAnswer, setMigrateAnswer] = useState<string>("Noch nicht ausgeführt");

    async function migrateSlugs() {
        let slugsMigrated:boolean = false;
        try {
            const response = await axios.get("/eyf/job/migrate/slugs");
            slugsMigrated = response.data;
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        } finally {
            if(slugsMigrated) {
                setMigrateAnswer("Slugs wurden migriert.");
            }
        }
    }
    return(
        <>
            <div className="flex flex-col justify-start">
                <table>
                    <thead>
                        <tr className= "border">
                            <th className= "bordertable p-1">Aufgabe</th>
                            <th className= "bordertable p-1">Aufgabenbeschreibung</th>
                            <th className= "bordertable p-1">Aktion</th>
                            <th className= "bordertable p-1">Stand</th>
                        </tr>
                    </thead>
                    <tbody>
                    <Job
                        task = "Migration"
                        description={"migriere Slugs in existierende Rezepte und Zutaten"}
                        onClick={migrateSlugs}
                        answer={migrateAnswer}
                    />
                    </tbody>
                    <tfoot>
                        <tr className= "border">
                            <th className= "bordertable p-1">Aufgabe</th>
                            <th className= "bordertable p-1">Aufgabenbeschreibung</th>
                            <th className= "bordertable p-1">Aktion</th>
                            <th className= "bordertable p-1">Stand</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </>
    )
}